package org.mapleleaf.backend.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.mapleleaf.backend.dto.problem.ProblemDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(value = "[문제 리스트 페이지]")
@RequestMapping("/v1/problemset")
@RestController
@Slf4j
public class ProblemsetController {
    @ApiOperation(value="모든 problem정보")
    @GetMapping("")
    List<ProblemDto> problemset() {
        log.info("problems all");
        return new ArrayList<>();
    }
}
