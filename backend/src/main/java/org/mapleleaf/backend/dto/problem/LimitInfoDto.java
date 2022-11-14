package org.mapleleaf.backend.dto.problem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class LimitInfoDto {
    @ApiModelProperty(
            value="메모리 제약(MB)",
            example = "128"
    )
    int memory;

    @ApiModelProperty(
            value="시간 제약(s)",
            example="1"
    )
    int time;
}
