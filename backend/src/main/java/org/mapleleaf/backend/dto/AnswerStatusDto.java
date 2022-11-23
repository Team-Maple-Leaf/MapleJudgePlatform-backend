package org.mapleleaf.backend.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.mapleleaf.backend.entity.Answer;
import org.mapleleaf.backend.entity.AnswerState;
import org.mapleleaf.backend.entity.Result;

@Data
@ApiModel
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerStatusDto {
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
    private Long answerId;

    public AnswerStatusDto(final AnswerState state) {
        result = state.getResult();
        time = state.getTime();
        memory = state.getMemory();
    }

    public AnswerStatusDto(final Answer answer) {
        AnswerState answerState = answer.getStateId();
        result = answerState.getResult();
        time = answerState.getTime();
        memory = answerState.getMemory();
        answerId = answerState.getId();
    }
}
