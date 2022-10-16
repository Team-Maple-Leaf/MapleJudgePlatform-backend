package org.mapleleaf.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapleleaf.backend.dto.AnswerDto;
import org.mapleleaf.backend.dto.SubmitDto;
import org.mapleleaf.backend.entity.Answer;
import org.mapleleaf.backend.entity.Problem;
import org.mapleleaf.backend.repository.AnswerRepository;
import org.mapleleaf.backend.repository.ProblemRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final ProblemRepository problemRepository;


    public AnswerDto getAnswer(Long id)
    {
        return new AnswerDto(
                        answerRepository
                                .findById(id)
                                .orElseThrow(IllegalArgumentException::new));
    }

    public List<AnswerDto> getAll()
    {
        return answerRepository.findAll()
                .stream()
                .map(AnswerDto::new)
                .collect(Collectors.toList());
    }

    public Answer submit(SubmitDto submitDto, Long problemId) {
        if (submitDto.getCode() == null || submitDto.getLanguage() == null || submitDto.getUserId() == null)
            throw new IllegalArgumentException();
        Problem problem = problemRepository.findById(problemId).orElseThrow(IllegalArgumentException::new);
        log.info("problem id in answer service: {} ", problem.getId());
        Answer answer = submitDto.toAnswerEntityWithProblem(problem);
        return answerRepository.save(answer);
    }

    public Boolean hasProblem(Long problemId) {
        return problemRepository.existsById(problemId);
    }
}
