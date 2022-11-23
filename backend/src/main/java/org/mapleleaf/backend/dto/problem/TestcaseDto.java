package org.mapleleaf.backend.dto.problem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.mapleleaf.backend.entity.Testcase;

@ApiModel
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class TestcaseDto {
    @ApiModelProperty(
            value="입력 예시",
            example="")
    String input;
    @ApiModelProperty(
            value="출력 예시",
            example="Hello World!\n")
    String output;

    public Testcase toTestcaseEntity() {
        return Testcase.builder()
                .input(getInput())
                .output(getOutput())
                .build();
    }

}
