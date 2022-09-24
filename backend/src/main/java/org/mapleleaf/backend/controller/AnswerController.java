package org.mapleleaf.backend.controller;

import io.swagger.annotations.*;
import org.mapleleaf.backend.dto.Answer;
import org.mapleleaf.backend.dto.Submit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(value = "답(answer)와 관련된 EP")
@RequestMapping("/api/v1")
@RestController
public class AnswerController {
    private static final Logger logger = LoggerFactory.getLogger(AnswerController.class);

    @ApiOperation(value="특정 answer 에대한 정보")
    @GetMapping("/answers/{id}")
    public Answer one(@PathVariable String id) {
        logger.info("answers one: " + "id: {}", id);
        return new Answer();
    }

    @ApiOperation(value="모든 answer의 정보")
    @GetMapping("/answers")
    public List<Answer> all() {
        logger.info("answer all");
        return new ArrayList<>();
    }

    @ApiOperation("특정 유저가 제출한 answer")
    @GetMapping("/users/{userId}/answers")
    public List<Answer> userAnswers(@PathVariable String userId) {
        logger.info("answers one: " + "id: {}", userId);
        return new ArrayList<>();
    }

    @ApiOperation("특정 문제에 제출된 answer")
    @GetMapping("/problems/{problemId}/answers")
    public List<Answer> problemAnswers(@PathVariable String problemId) {
        logger.info("answers one: " + "id: {}", problemId);
        return new ArrayList<>();
    }
}
