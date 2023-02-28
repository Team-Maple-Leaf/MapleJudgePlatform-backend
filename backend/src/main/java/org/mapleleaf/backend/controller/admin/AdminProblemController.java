package org.mapleleaf.backend.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapleleaf.backend.controller.template.ResponseTemplate;
import org.mapleleaf.backend.dto.problem.ProblemSubmitDto;
import org.mapleleaf.backend.dto.problem.TestcaseDto;
import org.mapleleaf.backend.dto.response.BasicResponse;
import org.mapleleaf.backend.entity.Testcase;
import org.mapleleaf.backend.service.ProblemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "[문제 상세정보 페이지]")
@RequestMapping("/v1/admin")
@RestController
@Slf4j
@RequiredArgsConstructor
public class AdminProblemController {

    private final ProblemService problemService;

    @ApiOperation(value="problem 제출")
    @PostMapping("/problem")
    public ResponseEntity<BasicResponse<Long>> submit(@RequestBody ProblemSubmitDto submit) {
        log.info("submit problem");
        return ResponseTemplate.execute(
                "문제 등록에 성공했습니다.",
                "문제 등록에 실패했습니다.",
                () -> problemService.submit(submit),
                HttpStatus.BAD_REQUEST
        );
    }

    @ApiOperation(value="problem testcase 조회")
    @GetMapping("/testcase/{problemId}")
    public ResponseEntity<BasicResponse<List<TestcaseDto>>> getTestcases(@PathVariable Long problemId) {
        log.info("{}번 문제 testcase 조회", problemId);
        return ResponseTemplate.execute(
                "테스트케이스 조회에 성공했습니다.",
                "테스트케이스 조회에 실패했습니다.",
                () -> problemService.getAllTestcasesWithProblemId(problemId),
                HttpStatus.BAD_REQUEST
        );
    }

    @ApiOperation(value = "problem 삭제")
    @DeleteMapping("/problem/{problemId}")
    public ResponseEntity<BasicResponse<Long>> deleteProblem(@PathVariable Long problemId) {
        log.info("delete problem");
        return ResponseTemplate.execute(
                "문제 삭제에 성공했습니다.",
        "문제 삭제에 실패했습니다.",
                () ->problemService.delete(problemId),
                HttpStatus.BAD_REQUEST
        );
    }
}
