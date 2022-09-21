package org.mapleleaf.backend.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel
public class AnswerState {
    public enum Result {
        ACCEPTED,
        PROCEEDING,
        WRONG_ANSWER,
        TIME_LIMIT_EXCEEDED,
        MEMORY_LIMIT_EXCEEDED,
        COMPILE_ERROR,
        RUNTIME_ERROR;
    }

    @ApiModelProperty(
            value="제출 답안의 체점 상태"
    )
    private Result result;
    @ApiModelProperty(
            value="사용한 시간"
    )
    private int time;
    @ApiModelProperty(
            value="사용한 메모리"
    )
    private int memory;
}
