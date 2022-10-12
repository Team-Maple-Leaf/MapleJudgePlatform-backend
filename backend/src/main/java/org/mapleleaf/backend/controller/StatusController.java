package org.mapleleaf.backend.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapleleaf.backend.exception.BadRequestException;
import org.mapleleaf.backend.exception.NotFoundException;
import org.mapleleaf.backend.dto.AnswerStatusDto;
import org.mapleleaf.backend.service.AnswerStateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "[제출 결과 페이지]")
@RequestMapping("/v1/status")
@RestController
@RequiredArgsConstructor
@Slf4j
public class StatusController {
    private final AnswerStateService service;
    @ApiOperation(value = "특정 answer 제출 답안의 결과")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "user_id값 혹은 problem_id가 존재하지 않습니다."),
            @ApiResponse(code = 401, message = "user_id와 일치하는 user가 없습니다."),
            @ApiResponse(code = 404, message = "일치하는 answer가 없습니다.")
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
    @GetMapping("")
    public ResponseEntity<AnswerStatusDto> status(
            @RequestParam(value="problem_id", required = false)Long problemId,
            @RequestParam(value="user_id", required = false)String userId) {
        log.info("problem id: {}, user id: {}", problemId, userId);
        if (problemId == null || userId == null) {
            log.error("user_id값 혹은 problem_id가 존재하지 않습니다.");
            throw new BadRequestException("user_id값 혹은 problem_id가 존재하지 않습니다.");
        }
        try {
            return ResponseEntity.ok(service.getStateByProblemIdAndUserId(problemId, userId));
        } catch (Exception e) {
            log.error("일치하는 answer가 없습니다.");
            throw new NotFoundException("일치하는 answer가 없습니다.");
        }
    }

}
