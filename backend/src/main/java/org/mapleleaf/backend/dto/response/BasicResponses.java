package org.mapleleaf.backend.dto.response;

import org.springframework.http.HttpStatus;

import java.util.Collections;

public class BasicResponses {

    static public <T> BasicResponse<T> getOkResponse(String message, T data) {
        return BasicResponse.<T>builder()
                .code(HttpStatus.OK.value())
                .message(message)
                .data(data)
                .build();
    }

    static public <T> BasicResponse<T> getNotFoundResponse(String message) {
        return BasicResponse.<T>builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message(message)
                .data((T) Collections.emptyList())
                .build();
    }

    static public <T> BasicResponse<T> getBadRequestResponse(String message) {
        return BasicResponse.<T>builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(message)
                .data((T) Collections.emptyList())
                .build();
    }

    static public <T> BasicResponse<T> getUnauthorizedResponse(String message) {
        return BasicResponse.<T>builder()
                .code(HttpStatus.UNAUTHORIZED.value())
                .message(message)
                .data((T) Collections.emptyList())
                .build();
    }
}
