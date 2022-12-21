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
import org.mapleleaf.backend.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth/")
@Slf4j
public class AuthController {
    private final AuthService authService;

    /* 토큰 생성 컨트롤러 */
    @ApiOperation(value = "login 요청")
    @ApiResponses(value = {
            @ApiResponse(code=401, message="토큰 값이 전달되지 않았습니다"),
            @ApiResponse(code=401, message="지원하지 않는 JWT 토큰입니다."),
            @ApiResponse(code=402, message="손상된 JWT 토큰입니다."),
            @ApiResponse(code=403, message="만료된 JWT 토큰입니다."),
            @ApiResponse(code=403, message="시그니처 검증에 실패한 토큰입니다.")})
    @PostMapping("login")
    public ResponseEntity<BasicResponse<TokenDto>> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseTemplate.execute(
                "토큰 발급에 성공했습니다.",
                "유효하지 않은 RequestBody입니다.",
                () -> authService.checkRequestAndCreateToken(loginRequestDto),
                HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("logout")
    public ResponseEntity<BasicResponse<List<Object>>> logout(@RequestHeader("Authorization") String bearerToken){
        return ResponseTemplate.execute(
                "로그아웃 되었습니다.",
                "잘못된 요청입니다.",
                () -> authService.logout(bearerToken),
                HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("refresh")
    public ResponseEntity<BasicResponse<TokenDto>> refresh(@RequestBody TokenDto tokenDto){
        return ResponseTemplate.execute(
                "토큰 재발급에 성공했습니다.",
                "잘못된 요청입니다.",
                () -> authService.refresh(tokenDto),
                HttpStatus.UNAUTHORIZED);
    }
}
