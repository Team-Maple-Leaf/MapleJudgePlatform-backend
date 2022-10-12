package org.mapleleaf.backend.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.mapleleaf.backend.dto.AnswerDto;
import org.mapleleaf.backend.dto.DtoConvertor;
import org.mapleleaf.backend.dto.problem.ExampleDto;
import org.mapleleaf.backend.dto.problem.ProblemDto;
import org.mapleleaf.backend.entity.Example;
import org.mapleleaf.backend.entity.Problem;
import org.mapleleaf.backend.exception.NotFoundException;
import org.mapleleaf.backend.repository.ExampleRepository;
import org.mapleleaf.backend.repository.ProblemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor //Lombok이 초기화되지 않은 필드를 생성해줘서 의존성 주입을 할 수 있음
public class ProblemService {

    private final ProblemRepository problemRepository;
    private final ExampleRepository exampleRepository;
    private final DtoConvertor dtoConvertor;

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

}
