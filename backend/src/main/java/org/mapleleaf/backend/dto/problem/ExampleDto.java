package org.mapleleaf.backend.dto.problem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.mapleleaf.backend.entity.Example;

@ApiModel
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    public Example toExampleEntity() {
        return Example.builder()
                .input(getInput())
                .output(getOutput())
                .build();
    }
}
