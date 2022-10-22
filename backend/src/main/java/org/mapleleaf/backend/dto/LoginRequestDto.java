package org.mapleleaf.backend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

    @ApiModelProperty(
            value="unique 값",
            required=true,
            example="1000")
    private String maple;

    @ApiModelProperty(
            value="user 이름",
            required=true,
            example="maple-leaf")
    private String name;

    @ApiModelProperty(
            value="user의 프로필 이미지",
            required=true,
            example="http://naver.com/myimage.png")
    private String picture;

    @ApiModelProperty(
            value="유효성 검사 값",
            required=true,
            example="86DFAp3536dag4fggSu=34")
    private String leaf;
}
