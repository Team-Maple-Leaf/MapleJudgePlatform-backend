package org.mapleleaf.backend.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapleleaf.backend.controller.template.ResponseTemplate;
import org.mapleleaf.backend.dto.AnswerDto;
import org.mapleleaf.backend.dto.response.BasicResponse;
import org.mapleleaf.backend.service.AnswerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "[답 상세정보 페이지]")
@RequestMapping("/v1/answers")
@RestController
@RequiredArgsConstructor
@Slf4j
public class AnswerController {

    private final AnswerService service;

    @ApiOperation(value="특정 answer 에대한 정보")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "404",
                    description = "id에 맞는 answer가 존재하지 않습니다.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BasicResponse<AnswerDto>> one(@PathVariable String id) {
        log.info("answers one: " + "id: {}", id);
        return ResponseTemplate.execute(
                "answer 조회에 성공했습니다.",
                "id에 맞는 answer가 존재하지 않습니다.",
                () -> service.getAnswer(Long.parseLong(id)),
                HttpStatus.NOT_FOUND
        );
    }

    @ApiOperation(value="모든 answer의 정보")
    @GetMapping("")
    public ResponseEntity<BasicResponse<List<AnswerDto>>> all() {
        log.info("answer all");
        return ResponseTemplate.execute(
                "전체 Answer에 대한 조회를 성공했습니다.",
                "전체 Answer에 대한 조회를 실패했습니다.",
                service::getAll,
                HttpStatus.NOT_FOUND
        );
    }
}
