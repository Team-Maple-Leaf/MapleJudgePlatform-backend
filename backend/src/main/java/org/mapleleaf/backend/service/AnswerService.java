package org.mapleleaf.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapleleaf.backend.amqp.AmqpSender;
import org.mapleleaf.backend.dto.*;
import org.mapleleaf.backend.dto.problem.TestcaseDto;
import org.mapleleaf.backend.entity.Answer;
import org.mapleleaf.backend.entity.Member;
import org.mapleleaf.backend.entity.Problem;
import org.mapleleaf.backend.repository.AnswerRepository;
import org.mapleleaf.backend.repository.MemberRepository;
import org.mapleleaf.backend.repository.ProblemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Answer (문제에 대한 답을 담고있는 class)를 위한 Service class
 * @author donggyu
 */
@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final ProblemRepository problemRepository;
    private final MemberRepository memberRepository;
    private final AmqpSender amqpSender;
    private final DtoConvertor convertor;

    /**
     * @param id 찾고싶은 Answer의 id
     * @return repository에서 찾은 AnswerDto를 리턴
     * @see AnswerDto
     * @see Answer
     * @throws IllegalArgumentException id에 맞는 Answer를 찾지 못할 때
     */
    public AnswerDto getAnswer(Long id)
    {
        AnswerDto dto = new AnswerDto(
                        answerRepository
                                .findById(id)
                                .orElseThrow(IllegalArgumentException::new));
        Member member = memberRepository.findByMaple(dto.getUserId());
        dto.setUserId(member.getName());
        return dto;
    }

    /**
     * @return repository에서 찾은 모든 Answer를 Dto리턴
     * @see AnswerDto
     * @see Answer
     */
    public List<AnswerDto> getAll()
    {
        return answerRepository.findAll()
                .stream()
                .map(AnswerDto::new)
                .peek(dto -> {
                    String s = dto.getUserId();
                    Member member = memberRepository.findByMaple(s);
                    dto.setUserId(member.getName());
                })
                .collect(Collectors.toList());
    }

    /**
     * @param submitDto repository에 저장할 정보를 담은 Dto
     * @param problemId 제출할 Answer의 문제 id
     * @return 저장한 Answer정보를 Dto로 리턴
     * @see SubmitDto
     * @see AnswerDto
     */
    public AnswerDto submit(SubmitDto submitDto, Long problemId) {
        if (submitDto.getCode() == null || submitDto.getLanguage() == null || submitDto.getUserId() == null)
            throw new IllegalArgumentException();
        Problem problem = problemRepository.findById(problemId).orElseThrow(IllegalArgumentException::new);
        List<TestcaseDto> testcaseDto = problem.getTestcases().stream().map(convertor::toDto).collect(Collectors.toList());
        log.info("problem id in answer service: {} ", problem.getId());
        Answer answer = submitDto.toAnswerEntityWithProblem(problem);
        answer = answerRepository.save(answer);
        ToJudgeDto judgeDto = ToJudgeDto.builder()
                .answerId(answer.getId())
                .language(answer.getLanguage())
                .code(answer.getCode())
                .testcases(testcaseDto)
                .memoryLimit(problem.getLimitMemory())
                .timeLimit(problem.getLimitTime())
                .build();
        amqpSender.send(judgeDto);
        return new AnswerDto(answer);
    }

    /**
     * problem id에 해당하는 Problem이 존재하는 지 확인
     * @param problemId 확인 할 problem의 id
     * @return problem을 찾을 수 있다면 true 아니면 false
     */
    public Boolean hasProblem(Long problemId) {
        return problemRepository.existsById(problemId);
    }

    /**
     * problem id에 해당하는 Problem 중 제출한 모든 Answer의 상태를 리턴
     * @param problemId 확인 할 Problem의 id
     * @param userId 확인 할 Answer을 제출한 user id
     * @return Answer의 체점 상태들의 list
     * @see AnswerDto
     */
    public List<AnswerStatusDto> getAllStateByProblemAndUserId(Long problemId, String userId) {
        if (!hasProblem(problemId))
            throw new IllegalArgumentException();
        List<Answer> answers = answerRepository.findAllByProblemId_IdAndUserId(problemId, userId);
        return answers.stream()
                .map(AnswerStatusDto::new)
                .collect(Collectors.toList());
    }

    /**
     * answer id에 맞는 체점 상태 하나 리턴
     * @param answerId 체점 상태를 확인하고 싶은 answer의 id
     * @return AnswerStatus 정보를 Dto로 리턴
     * @see AnswerStatusDto
     */
    public AnswerStatusDto getOneState(Long answerId) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(IllegalArgumentException::new);
        return new AnswerStatusDto(answer);
    }

    /**
     * 페이지네이션 기능이 추가된 문제 페이지
     * @param pageable 페이징을 제공하는 인터페이스, PageRequest 객체를 사용한다.
     * @return Page<AnswerDto>
     * @see AnswerDto
     */
    public Page<AnswerDto> getAllPagingAnswers(Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), (int) answerRepository.count());

        List<AnswerDto> answerDtoList = answerRepository.findAll(pageable).stream()
                .map(AnswerDto::new)
                .peek(dto -> {
                    String userName = memberRepository.findByMaple(dto.getUserId()).getName();
                    dto.setUserId(userName);})
                .collect(Collectors.toList());

        return new PageImpl<>(answerDtoList.subList(start, end), pageable, answerRepository.count());
    }
}
