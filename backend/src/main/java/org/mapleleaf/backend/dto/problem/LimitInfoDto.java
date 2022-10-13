package org.mapleleaf.backend.dto.problem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class LimitInfoDto {
    @ApiModelProperty(
            value="메모리 제약",
            example = "128 MB"
    )
    String memory;

    @ApiModelProperty(
            value="시간 제약",
            example="1 초"
    )
    String time;

    public LimitInfoDto(String memory, String time) {
        this.memory = memory;
        this.time = time;
    }
}
