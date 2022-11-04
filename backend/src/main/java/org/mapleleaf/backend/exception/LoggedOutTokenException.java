package org.mapleleaf.backend.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
@NoArgsConstructor
public class LoggedOutTokenException extends RuntimeException {
    public LoggedOutTokenException(String msg) {
        super(msg);
    }
}
