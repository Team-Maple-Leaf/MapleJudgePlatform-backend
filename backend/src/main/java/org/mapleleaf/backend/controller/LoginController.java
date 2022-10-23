package org.mapleleaf.backend.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapleleaf.backend.controller.template.ResponseTemplate;
import org.mapleleaf.backend.dto.LoginRequestDto;
import org.mapleleaf.backend.dto.TokenDto;
import org.mapleleaf.backend.dto.response.BasicResponse;
import org.mapleleaf.backend.service.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/login")
@Slf4j
public class LoginController {
    private final LoginService loginService;

    /* 토큰 생성 컨트롤러 */
    @ApiOperation(value = "login 요청")
    @ApiResponses(value = {
            @ApiResponse(code=401, message="토큰 값이 전달되지 않았습니다"),
            @ApiResponse(code=401, message="지원하지 않는 JWT 토큰입니다."),
            @ApiResponse(code=402, message="손상된 JWT 토큰입니다."),
            @ApiResponse(code=403, message="만료된 JWT 토큰입니다."),
            @ApiResponse(code=403, message="시그니처 검증에 실패한 토큰입니다.")})
    @PostMapping("/")
    public ResponseEntity<BasicResponse<TokenDto>> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseTemplate.execute(
                "토큰 발급에 성공했습니다.",
                "유효하지 않은 RequestBody입니다.",
                () -> loginService.checkRequestAndCreateToken(loginRequestDto),
                HttpStatus.UNAUTHORIZED);
    }
}
