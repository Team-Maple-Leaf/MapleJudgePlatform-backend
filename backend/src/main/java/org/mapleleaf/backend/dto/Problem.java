package org.mapleleaf.backend.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class Problem {
    @ApiModelProperty(
            value="문제 id",
            accessMode=ApiModelProperty.AccessMode.READ_ONLY
    )
    private Long id;
    @ApiModelProperty(
            value="문제 제목",
            required = true,
            example="Hello World 출력"
    )
    private String title;
    @ApiModelProperty(
            value="문제 설명",
            required = true,
            example="Hello World를 출력해보자"
    )
    private String problemDescription;
    @ApiModelProperty(
            value="입력 설명",
            required = true,
            example="이 문제는 입력이 없습니다."
    )
    private String inputDescription;
    @ApiModelProperty(
            value="출력 설명",
            required = true,
            example="Hello World!"
    )
    private String outputDescription;
    @ApiModelProperty(
            "입력과 출력의 예시"
    )
    private List<Example> examples;
}
