package org.mapleleaf.backend.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapleleaf.backend.dto.problem.ProblemDto;
import org.mapleleaf.backend.entity.BasicResponse;
import org.mapleleaf.backend.exception.NotFoundException;
import org.mapleleaf.backend.service.ProblemService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "[문제 상세정보 페이지]")
@RequestMapping("/v1/problem")
@RestController // @Controller + @ResponseBody -> JSON 형태로 객체 데이터 반환 가능
@Slf4j // 로그를 남기는 방식 중 logger 클래스 사용 가능
@RequiredArgsConstructor
public class ProblemController {

    // Valid: @NotBlank와 같은 유효성 검사를 해주는 어노테이션

    private final ProblemService problemService;

    @ApiOperation(value="특정 problem정보")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "problem_id에 일치하는 문제가 없습니다.")
    })
    @GetMapping("/{problemId}")
    public ResponseEntity<?> getProblem(@PathVariable Long problemId) {
        try {
            ProblemDto problemDto = problemService.getProblem(problemId);
            BasicResponse basicResponse = new BasicResponse();
            basicResponse = BasicResponse.builder()
                    .code(HttpStatus.OK.value())
                    .httpStatus(HttpStatus.OK)
                    .message(problemId + "번 문제 조회에 성공했습니다.")
                    .data(problemDto)
                    .build();

            log.info("get a problem " + problemId);

            return new ResponseEntity<>(basicResponse, HttpStatus.OK);

        } catch (NotFoundException e) {
            BasicResponse basicResponse = BasicResponse.builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .message(problemId + "번 문제가 존재하지 않습니다.")
                    .data(null)
                    .build();

            HttpHeaders errHeaders = new HttpHeaders(); // 한글깨짐 수정
            errHeaders.add("Content-Type", "application/json;charset=UTF-8");
            log.info("fail to get a problem " + problemId);
            return new ResponseEntity<>(basicResponse, errHeaders, HttpStatus.NOT_FOUND);
        }
    }
}
