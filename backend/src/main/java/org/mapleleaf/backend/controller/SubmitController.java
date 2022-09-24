package org.mapleleaf.backend.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.mapleleaf.backend.dto.Submit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@Api(value = "[코드 제출 페이지]")
@RequestMapping("/api/v1/submit")
@RestController
public class SubmitController {
    private static final Logger logger = LoggerFactory.getLogger(SubmitController.class);

    @ApiOperation(value="answer 제출")
    @PostMapping("/{problemId}")
    public void submit(@PathVariable Long problemId, @RequestBody Submit submit) {
        logger.info("problem id: {}, answer: {}", problemId, submit.toString());
    }
}
