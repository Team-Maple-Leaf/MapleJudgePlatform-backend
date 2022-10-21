package org.mapleleaf.backend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapleleaf.backend.dto.LoginRequestDto;
import org.mapleleaf.backend.dto.TokenDto;
import org.mapleleaf.backend.dto.response.BasicResponse;
import org.mapleleaf.backend.jwt.JwtProvider;
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
    private final JwtProvider jwtProvider;

    /* 토큰 생성 컨트롤러 */
    @PostMapping("/")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        if(loginService.isValidRequest(loginRequestDto)) {
            log.info("유효성 검사 통과");
            if(!loginService.memberExist(loginRequestDto.getMaple())){
                log.info("존재하지 않는 멤버");
                loginService.join(loginRequestDto);
            }

            String uuid = loginService.getUUID(loginRequestDto.getMaple());
            String token = jwtProvider.createToken(uuid); // 토큰 생성

            return new ResponseEntity<>(BasicResponse.builder()
                    .code(HttpStatus.OK.value())
                    .message("토큰 발급 성공")
                    .data(new TokenDto(token))
                    .build(),  HttpStatus.OK);
        } else {
            log.info("유효하지 않은 Request");
            return new ResponseEntity<>(BasicResponse.builder()
                    .code(HttpStatus.UNAUTHORIZED.value())
                    .message("유효하지 않은 Request입니다.")
                    .data("")
                    .build(),  HttpStatus.UNAUTHORIZED);
        }
    }
}
