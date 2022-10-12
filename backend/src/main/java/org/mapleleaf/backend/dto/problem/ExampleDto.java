package org.mapleleaf.backend.dto.problem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.mapleleaf.backend.entity.Problem;

@Builder
@ApiModel
@Getter
public class ExampleDto {
    @ApiModelProperty(
            value="입력 예시",
            example="")
    String input;
    @ApiModelProperty(
            value="출력 예시",
            example="Hello World!\n")
    String output;
}
