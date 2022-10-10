package org.mapleleaf.backend.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapleleaf.backend.controller.status.NotFoundException;
import org.mapleleaf.backend.dto.AnswerDto;
import org.mapleleaf.backend.service.AnswerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
            @ApiResponse(code = 404, message = "id에 맞는 answer가 존재하지 않습니다."),
    })
    @GetMapping("/{id}")
    public ResponseEntity<AnswerDto> one(@PathVariable String id) {
        log.info("answers one: " + "id: {}", id);
        try {
            long idL;
            idL = Long.parseLong(id);
            return ResponseEntity.ok().body(service.getAnswer(idL));
        } catch (Exception e) {
            log.error("id에 맞는 answer가 존재하지 않습니다. {}", id);
            throw new NotFoundException("id에 맞는 answer가 존재하지 않습니다.");
        }
    }

    @ApiOperation(value="모든 answer의 정보")
    @GetMapping("")
    public ResponseEntity<List<AnswerDto>> all() {
        log.info("answer all");
        return ResponseEntity.ok(service.getAll());
    }
}
