package org.mapleleaf.backend.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapleleaf.backend.controller.template.ResponseTemplate;
import org.mapleleaf.backend.dto.problem.ProblemDto;
import org.mapleleaf.backend.dto.response.BasicResponse;
import org.mapleleaf.backend.service.ProblemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "[문제 리스트 페이지]")
@RequestMapping("/v1/problemset")
@RestController
@Slf4j
@RequiredArgsConstructor
public class ProblemsetController {

    private final ProblemService problemService;

    @ApiOperation(value="모든 problem정보")
    @ApiResponses(
            @ApiResponse(code = 404, message = "전체 문제에 대한 조회를 실패했습니다.")
    )
    @GetMapping("")
    public ResponseEntity<BasicResponse<List<ProblemDto>>> problemset() {
        log.info("get all problems.");
        return ResponseTemplate.execute(
                "전체 문제에 대한 조회를 성공했습니다.",
                "전체 문제에 대한 조회를 실패했습니다.",
                problemService::getAllProblems,
                HttpStatus.NOT_FOUND
        );
    }
}
