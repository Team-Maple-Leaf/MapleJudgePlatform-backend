package org.mapleleaf.backend.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.mapleleaf.backend.dto.problem.ProblemDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "[문제 상세정보 페이지]")
@RequestMapping("/v1/problem")
@RestController
public class ProblemController {
    private static final Logger logger = LoggerFactory.getLogger(ProblemController.class);

    @ApiOperation(value="특정 problem정보")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "problem_id에 일치하는 문제가 없습니다.")
    })
    @GetMapping("/{problemId}")
    ProblemDto one(@PathVariable Long problemId) {
        logger.info("problems one: " + problemId);
        return new ProblemDto();
    }
}
