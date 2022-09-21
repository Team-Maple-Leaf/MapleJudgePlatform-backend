package org.mapleleaf.backend.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class Submit {
    @ApiModelProperty(
            value="유저 id",
            example="1")
    private Long userId;
    @ApiModelProperty(
            value="답을 작성한 코드",
            required=true,
            example="#include <stdio.h>\nint main() {\n\tprintf(\"Hello World!\");\n\treturn 0;\n}")
    private String code;
}
