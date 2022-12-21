package org.mapleleaf.backend.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor
public class TokenDto {

    /*
        @NoArgsConstructor 추가한 이유
        오류: cannot deserialize from Object value (no delegate- or property-based Creator)
        오류 원인: jackson library가 빈 생성자가 없는 모델을 생성하는 방법을 몰라서 발생하는 오류
        해결: 기본 생성자를 추가한다.
     */

    @ApiModelProperty(
            value="accessToken",
            required = true,
            example="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
    private String accessToken;

    @ApiModelProperty(
            value="refreshToken",
            required = true,
            example="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
    private String refreshToken;


}
