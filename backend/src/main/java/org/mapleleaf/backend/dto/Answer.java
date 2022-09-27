package org.mapleleaf.backend.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel
public class Answer {

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
    private Long userId;
    @ApiModelProperty(
            value="제출 할 문제 id",
            required = true,
            example="1")
    private Long problemId;
    @ApiModelProperty(
            value="제출된 답안의 체점 상태",
            accessMode=ApiModelProperty.AccessMode.READ_ONLY,
            example="")
    private AnswerStatus state;
    @ApiModelProperty(
            value="답을 작성한 코드의 언어",
            required = true,
            example="c")
    private Language language;
    @ApiModelProperty(
            value="제출 시간"
    )
    private Date date;
    @ApiModelProperty(
            value="코드 길이"
    )
    private int codeLength;
}
