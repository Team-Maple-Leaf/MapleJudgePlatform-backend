package org.mapleleaf.backend.controller;

import io.swagger.annotations.*;
import org.mapleleaf.backend.dto.Answer;
import org.mapleleaf.backend.dto.AnswerStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@Api(value = "[제출 결과 페이지]")
@RequestMapping("/v1/status")
@RestController
public class StatusController {
    private static final Logger logger = LoggerFactory.getLogger(StatusController.class);

    @ApiOperation(value = "특정 answer 제출 답안의 결과")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "user_id값이 존재하지 않습니다."),
            @ApiResponse(code = 401, message = "user_id와 일치하는 user가 없습니다.")
    })
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(
                            name="problem_id",
                            value="문제 번호",
                            required = true,
                            dataType="query"),
                    @ApiImplicitParam(
                            name="user_id",
                            value="제출 유저 id",
                            required = true,
                            dataType="query")
            }
    )
    @GetMapping("/")
    public AnswerStatus status(
            @RequestParam(value="problem_id")Long problemId,
            @RequestParam(value="user_id")Long userId) {
        logger.info("problem id: {}, user id: {}", problemId, userId);
        return new AnswerStatus();
    }

}
