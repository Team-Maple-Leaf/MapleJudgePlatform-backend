package org.mapleleaf.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class Submit {
    @ApiModelProperty(
            value="유저 id",
            example="1")
    @JsonProperty("user_id")
    private Long userId;
    @ApiModelProperty(
            value="답을 작성한 코드",
            required=true,
            example="#include <stdio.h>\nint main() {\n\tprintf(\"Hello World!\");\n\treturn 0;\n}")
    private String code;
    @ApiModelProperty(
            value="답을 작성한 코드의 언어",
            required = true,
            example="c")
    private Answer.Language language;

}
