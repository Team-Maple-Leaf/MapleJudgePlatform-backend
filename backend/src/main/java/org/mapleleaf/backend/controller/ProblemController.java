package org.mapleleaf.backend.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapleleaf.backend.dto.problem.ProblemDto;
import org.mapleleaf.backend.entity.Problem;
import org.mapleleaf.backend.exception.NotFoundException;
import org.mapleleaf.backend.service.ProblemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

            log.info("problems one: " + problemId);
            return new ResponseEntity<ProblemDto>(problemDto, HttpStatus.OK);

        } catch (NotFoundException e) {
            log.info(String.valueOf(e.getStackTrace()));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
