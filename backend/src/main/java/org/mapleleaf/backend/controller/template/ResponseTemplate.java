package org.mapleleaf.backend.controller.template;

import lombok.extern.slf4j.Slf4j;
import org.mapleleaf.backend.dto.response.BasicResponse;
import org.mapleleaf.backend.dto.response.BasicResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Slf4j
public class ResponseTemplate{
    @FunctionalInterface
    public interface DelayData<T> {
        T get();
    }

    static public <T> ResponseEntity<BasicResponse<T>> execute(String successMsg, String failedMsg, DelayData<T> data, HttpStatus failedType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            BasicResponse<T> basicResponse =
                    BasicResponses.getOkResponse(
                            successMsg,
                            data.get()
                    );
            log.info(successMsg);
            return new ResponseEntity<>(basicResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.info(failedMsg);
            if (failedType == HttpStatus.NOT_FOUND) {
                BasicResponse<T> basicResponse =
                        BasicResponses.getNotFoundResponse(failedMsg);
                return new ResponseEntity<>(basicResponse, headers, HttpStatus.NOT_FOUND);
            } else if(failedType == HttpStatus.UNAUTHORIZED) {
                BasicResponse<T> basicResponse =
                        BasicResponses.getUnauthorizedResponse(failedMsg);
                return new ResponseEntity<>(basicResponse, headers, HttpStatus.UNAUTHORIZED);
            }
            else {
                throw new IllegalArgumentException("failedType is not justify");
            }
        }
    }
}
