package org.mapleleaf.backend.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class Example {
    @ApiModelProperty(
            value="입력 예시",
            example="")
    String input;
    @ApiModelProperty(
            value="출력 예시",
            example="Hello World!\n")
    String output;
}
