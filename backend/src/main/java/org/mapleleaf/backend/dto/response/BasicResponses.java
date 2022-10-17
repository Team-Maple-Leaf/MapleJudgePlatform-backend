package org.mapleleaf.backend.dto.response;

import org.springframework.http.HttpStatus;

public class BasicResponses {
    static public BasicResponse getOkResponse(String message, Object data) {
        return BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message(message)
                .data(data)
                .build();
    }
    static public BasicResponse getNotFoundResponse(String message) {
        return BasicResponse.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(message)
                .data(null)
                .build();
    }

    static public BasicResponse getBadRequestResponse(String message) {
        return BasicResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .httpStatus((HttpStatus.BAD_REQUEST))
                .message(message)
                .data(null)
                .build();
    }
}
