package org.mapleleaf.backend.dto;

import org.mapleleaf.backend.dto.problem.ExampleDto;
import org.mapleleaf.backend.dto.problem.LimitInfoDto;
import org.mapleleaf.backend.dto.problem.ProblemDto;
import org.mapleleaf.backend.dto.problem.TestcaseDto;
import org.mapleleaf.backend.entity.Example;
import org.mapleleaf.backend.entity.Problem;
import org.mapleleaf.backend.entity.Testcase;
import org.springframework.stereotype.Component;

@Component
public class DtoConvertor {
    public ProblemDto toDto(Problem problem){
        return ProblemDto.builder()
                .no(problem.getId())
                .title(problem.getTitle())
                .problemDesc(problem.getProblemDesc())
                .inputDesc(problem.getInputDesc())
                .outputDesc(problem.getOutputDesc())
                .limitInfo(new LimitInfoDto(problem.getLimitMemory(), problem.getLimitTime()))
                .build();
    }

    public ExampleDto toDto(Example example){
        return ExampleDto.builder()
                .input(example.getInput())
                .output(example.getOutput())
                .build();
    }

    public TestcaseDto toDto(Testcase testcase) {
        return TestcaseDto.builder()
                .input(testcase.getInput())
                .output(testcase.getOutput())
                .build();
    }
}
