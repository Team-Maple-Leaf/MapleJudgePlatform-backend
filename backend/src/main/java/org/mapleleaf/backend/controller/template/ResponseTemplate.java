package org.mapleleaf.backend.controller.template;

import lombok.extern.slf4j.Slf4j;
import org.mapleleaf.backend.dto.response.BasicResponse;
import org.mapleleaf.backend.dto.response.BasicResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Slf4j
public class ResponseTemplate {
    @FunctionalInterface
    public interface DelayData {
        Object func();
    }
    static public ResponseEntity<?> execute(String successMsg, String failedMsg, DelayData data, HttpStatus failedType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            BasicResponse basicResponse =
                    BasicResponses.getOkResponse(
                            successMsg,
                            data.func()
                    );
            log.info(successMsg);
            return new ResponseEntity<>(basicResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.info(failedMsg);
            if (failedType == HttpStatus.NOT_FOUND) {
                BasicResponse basicResponse =
                        BasicResponses.getNotFoundResponse(failedMsg);
                return new ResponseEntity<>(basicResponse, headers, HttpStatus.NOT_FOUND);
            } else {
                throw new IllegalArgumentException("failedType is not justify");
            }
        }
    }
}
