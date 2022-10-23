package org.mapleleaf.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel
public class AnswerDto {

    public enum Language {
        C
    }
    @ApiModelProperty(
            value="답 id",
            accessMode=ApiModelProperty.AccessMode.READ_ONLY)
    private Long id;
    @ApiModelProperty(
            value="답을 작성한 코드",
            required=true,
            example="#include <stdio.h>\nint main() {\n\tprintf(\"Hello World!\");\n\treturn 0;\n}")
    private String code;
    @ApiModelProperty(
            value="답을 작성한 유저 id",
            required=true,
            example="1")
    @JsonProperty("user_id")
    private String userId;
    @ApiModelProperty(
            value="제출 할 문제 id",
            required = true,
            example="1")
    @JsonProperty("problem_id")
    private Long problemId;
    @ApiModelProperty(
            value="제출된 답안의 체점 상태",
            accessMode=ApiModelProperty.AccessMode.READ_ONLY,
            example="")
    private AnswerStatusDto state;
    @ApiModelProperty(
            value="답을 작성한 코드의 언어",
            required = true,
            example="C")
    private Language language;
    @ApiModelProperty(
            value="제출 시간"
    )
    private Date date;
    @ApiModelProperty(
            value="코드 길이"
    )
    @JsonProperty("code_length")
    private int codeLength;
}
