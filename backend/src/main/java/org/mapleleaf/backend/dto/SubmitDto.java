package org.mapleleaf.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.mapleleaf.backend.entity.Answer;
import org.mapleleaf.backend.entity.AnswerState;
import org.mapleleaf.backend.entity.Language;
import org.mapleleaf.backend.entity.Problem;

import java.util.Date;

@Data
@ApiModel
public class SubmitDto {
    @ApiModelProperty(
            value="유저 id",
            example="1")
    @JsonProperty("user_id")
    private String userId;
    @ApiModelProperty(
            value="답을 작성한 코드",
            required=true,
            example="#include <stdio.h>\nint main() {\n\tprintf(\"Hello World!\");\n\treturn 0;\n}")
    private String code;
    @ApiModelProperty(
            value="답을 작성한 코드의 언어",
            required = true,
            example="C")
    private Language language;
    public Answer toAnswerEntityWithProblem(final Problem problem)
    {
        return Answer.builder()
                .date(new Date())
                .code_length(code.length())
                .code(code)
                .problemId(problem)
                .stateId(new AnswerState())
                .userId(userId)
                .language(language)
                .build();
    }

}
