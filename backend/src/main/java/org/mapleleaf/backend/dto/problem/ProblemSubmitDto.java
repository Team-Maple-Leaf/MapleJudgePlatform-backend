package org.mapleleaf.backend.dto.problem;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.mapleleaf.backend.entity.Example;
import org.mapleleaf.backend.entity.Problem;
import org.mapleleaf.backend.entity.Testcase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@ApiModel
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Slf4j
public class ProblemSubmitDto {
     @ApiModelProperty(
            value="문제 id",
            accessMode=ApiModelProperty.AccessMode.READ_ONLY
    )
    private Long no;

    @ApiModelProperty(
            value="문제 제목",
            required = true,
            example="Hello World 출력"
    )
    private String title;

    @ApiModelProperty(value="문제 제약사항들")
    @JsonProperty("limit_info")
    private LimitInfoDto limitInfo;

    @ApiModelProperty(
            value="문제 설명",
            required = true,
            example="Hello World를 출력해보자"
    )
    @JsonProperty("problem_desc")
    private String problemDesc;

    @ApiModelProperty(
            value="입력 설명",
            required = true,
            example="이 문제는 입력이 없습니다."
    )
    @JsonProperty("input_desc")
    private String inputDesc;

    @ApiModelProperty(
            value="출력 설명",
            required = true,
            example="Hello World!"
    )
    @JsonProperty("output_desc")
    private String outputDesc;

    @ApiModelProperty("입력과 출력의 예시")
    @JsonProperty("io_examples")
    private List<ExampleDto> ioExamples;

    @ApiModelProperty("입력과 출력의 예시")
    @JsonProperty("io_testcases")
    private List<TestcaseDto> ioTestcases;
    public Problem toProblemEntity() {
        Problem ret = Problem.builder()
                .title(getTitle())
                .problemDesc(getProblemDesc())
                .inputDesc(getInputDesc())
                .outputDesc(getOutputDesc())
                .examples(new ArrayList<>())
                .testcases(new ArrayList<>())
                .limitMemory(getLimitInfo().memory)
                .limitTime(getLimitInfo().time)
                .build();
        log.info("examples: {}", getIoExamples().toString());
        log.info("testcases: {}", getIoTestcases().toString());
        getIoExamples().stream()
                .map(ExampleDto::toExampleEntity)
                .forEach(ret::addExample);

        getIoTestcases().stream()
                .map(TestcaseDto::toTestcaseEntity)
                .forEach(ret::addTestcase);
        return ret;
    }
}
