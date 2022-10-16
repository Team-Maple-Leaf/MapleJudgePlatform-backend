package org.mapleleaf.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasicResponse {
    private Integer code;
    private HttpStatus httpStatus;
    private String message;
    private Object data;
}
