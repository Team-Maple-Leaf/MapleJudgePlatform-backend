package org.mapleleaf.backend.controller.status;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String msg) {
        super(msg);
    }
}
