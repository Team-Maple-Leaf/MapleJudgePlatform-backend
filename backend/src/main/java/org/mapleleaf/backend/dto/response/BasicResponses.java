package org.mapleleaf.backend.dto.response;

import org.springframework.http.HttpStatus;

public class BasicResponses {
    static public <T> BasicResponse<T> getOkResponse(String message, T data) {
        return BasicResponse.<T>builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message(message)
                .data(data)
                .build();
    }
    static public <T> BasicResponse<T> getNotFoundResponse(String message) {
        return BasicResponse.<T>builder()
                .code(HttpStatus.NOT_FOUND.value())
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(message)
                .data(null)
                .build();
    }

    static public <T> BasicResponse<T> getBadRequestResponse(String message) {
        return BasicResponse.<T>builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .httpStatus((HttpStatus.BAD_REQUEST))
                .message(message)
                .data(null)
                .build();
    }
}
