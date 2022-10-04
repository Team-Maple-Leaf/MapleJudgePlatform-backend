package org.mapleleaf.backend.controller;

import io.swagger.annotations.*;
import org.mapleleaf.backend.dto.SubmitDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@Api(value = "[코드 제출 페이지]")
@RequestMapping("/v1/submit")
@RestController
public class SubmitController {
    private static final Logger logger = LoggerFactory.getLogger(SubmitController.class);

    @ApiOperation(value = "answer 제출")
    @ApiResponses(value = {
            @ApiResponse(code=404, message="problem_id에 맞는 문제가 존재하지 않습니다."),
            @ApiResponse(code=401, message="user_id와 일치하는 user가 없습니다."),
            @ApiResponse(code=400, message="payload가 올바르게 작성되지 않았습니다.")
            }
    )
    @PostMapping("/{problemId}")
    public void submit(@PathVariable Long problemId, @RequestBody SubmitDto submit) {
        logger.info("problem id: {}, answer: {}", problemId, submit.toString());
    }
}
