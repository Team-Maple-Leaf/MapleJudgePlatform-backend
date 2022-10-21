package org.mapleleaf.backend.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class BasicResponse<T> {

    @ApiModelProperty(
            value = "http 코드",
            example = "200"
    )
    private Integer code;

    @ApiModelProperty(
            value = "설명 메시지",
            example = "성공 메시지 입니다."
    )
    private String message;

    @ApiModelProperty(
            value = "특정 데이터"
    )
    private T data;
}
