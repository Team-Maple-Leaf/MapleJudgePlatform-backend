package org.mapleleaf.backend.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapleleaf.backend.dto.problem.ProblemDto;
import org.mapleleaf.backend.exception.NotFoundException;
import org.mapleleaf.backend.service.ProblemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(value = "[문제 리스트 페이지]")
@RequestMapping("/v1/problemset")
@RestController
@Slf4j
@RequiredArgsConstructor
public class ProblemsetController {

    private final ProblemService problemService;

    @ApiOperation(value="모든 problem정보")
    @GetMapping("")
    public ResponseEntity<?> problemset() {
        try {
            List<ProblemDto> problemDtoList = problemService.getAllProblems();

            log.info("problems all");
            return new ResponseEntity<List<ProblemDto>>(problemDtoList, HttpStatus.OK);

        } catch (NotFoundException e) {
            log.info(String.valueOf(e.getStackTrace()));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
