package org.mapleleaf.backend.controller;

import io.swagger.annotations.*;
import org.mapleleaf.backend.dto.AnswerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(value = "[답 상세정보 페이지]")
@RequestMapping("/v1/answers")
@RestController
public class AnswerController {
    private static final Logger logger = LoggerFactory.getLogger(AnswerController.class);

    @ApiOperation(value="특정 answer 에대한 정보")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "id에 맞는 answer가 존재하지 않습니다."),
    })
    @GetMapping("/{id}")
    public AnswerDto one(@PathVariable String id) {
        logger.info("answers one: " + "id: {}", id);
        return new AnswerDto();
    }

    @ApiOperation(value="모든 answer의 정보")
    @GetMapping("")
    public List<AnswerDto> all() {
        logger.info("answer all");
        return new ArrayList<>();
    }
}
