package org.mapleleaf.backend.dto.response;

import org.springframework.http.HttpStatus;

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
                .data(null)
                .build();
    }

    static public <T> BasicResponse<T> getBadRequestResponse(String message) {
        return BasicResponse.<T>builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(message)
                .data(null)
                .build();
    }
}
