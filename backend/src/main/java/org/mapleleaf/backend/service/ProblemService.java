package org.mapleleaf.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapleleaf.backend.dto.DtoConvertor;
import org.mapleleaf.backend.dto.problem.ExampleDto;
import org.mapleleaf.backend.dto.problem.ProblemDto;
import org.mapleleaf.backend.dto.problem.ProblemSubmitDto;
import org.mapleleaf.backend.dto.problem.TestcaseDto;
import org.mapleleaf.backend.entity.Example;
import org.mapleleaf.backend.entity.Problem;
import org.mapleleaf.backend.entity.Testcase;
import org.mapleleaf.backend.exception.NotFoundException;
import org.mapleleaf.backend.repository.ExampleRepository;
import org.mapleleaf.backend.repository.ProblemRepository;
import org.mapleleaf.backend.repository.TestcaseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor //Lombok이 초기화되지 않은 필드를 생성해줘서 의존성 주입을 할 수 있음
@Transactional
@Slf4j
public class ProblemService {

    private final ProblemRepository problemRepository;
    private final ExampleRepository exampleRepository;
    private final TestcaseRepository testcaseRepository;
    private final DtoConvertor dtoConvertor;


    public Long submit(ProblemSubmitDto submitDto) {
        if (submitDto.getTitle() == null
            || submitDto.getLimitInfo() == null)
            throw new IllegalArgumentException();
        Problem problem = submitDto.toProblemEntity();
        return problemRepository.save(problem).getId();
    }

    public List<ProblemDto> getAllProblems() {

        List<ProblemDto> problemDtoList = problemRepository.findAll().stream().map(dtoConvertor::toDto).collect(Collectors.toList());
        problemDtoList.forEach(dto -> dto.setIoExamples(
                exampleRepository.findByProblemId(dto.getNo()).stream().map(dtoConvertor::toDto).collect(Collectors.toList())
        ));

        return problemDtoList;
    }

    public ProblemDto getProblem(Long problemId){
        Problem problem = problemRepository.findById(problemId).orElseThrow(() ->
                new NotFoundException(problemId + "번 문제가 존재하지 않습니다."));
        ProblemDto problemDto = dtoConvertor.toDto(problem);

        List<Example> example = exampleRepository.findByProblemId(problemId);
        List<ExampleDto> exampleDto = example.stream()
                .map(dtoConvertor::toDto).collect(Collectors.toList());
        problemDto.setIoExamples(exampleDto);

        return problemDto;
    }

    public List<TestcaseDto> getAllTestcasesWithProblemId(Long problemId) {
        if (!problemRepository.existsById(problemId))
            throw new NotFoundException(problemId +"번 문제가 존재하지 않습니다.");
        List<Testcase> testcases = testcaseRepository.findByProblemId(problemId);
        return testcases.stream()
                .map(dtoConvertor::toDto)
                .collect(Collectors.toList());
    }

}
