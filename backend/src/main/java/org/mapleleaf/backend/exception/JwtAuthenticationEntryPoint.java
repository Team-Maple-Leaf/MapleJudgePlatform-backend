package org.mapleleaf.backend.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.mapleleaf.backend.dto.response.BasicResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;


@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String exception = (String)request.getAttribute("exception");

        // 토큰이 헤더에 없는 경우
        if(exception == null) {
            createResponse(401,"토큰 값이 전달되지 않았습니다", response);
        }

        else if(exception.equals("MalformedJwtException")){
            createResponse(402,"손상된 JWT 토큰입니다.", response);
        }

        else if(exception.equals("ExpiredJwtException")) {
            createResponse(401,"만료된 JWT 토큰입니다.", response);
        }

        else if(exception.equals("UnsupportedJwtException")) {
            createResponse(401,"지원하지 않는 JWT 토큰입니다.", response);
        }

        else if(exception.equals("SignatureException")) {
            createResponse(401,"시그니처 검증에 실패한 토큰입니다.", response);
        }
        else if(exception.equals("LoggedOutTokenException")) {
            createResponse(401, "로그아웃된 토큰입니다.", response);
        }
    }

    private void createResponse(int code, String msg, HttpServletResponse response) throws IOException{
        BasicResponse basicResponse = BasicResponse.builder()
                .code(code)
                .message(msg) // or 잘못된 접근입니다?
                .data(Collections.emptyList())
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(basicResponse);
        response.setContentType("application/json;charset=UTF-8");// json 형태로 출력, 한글 깨짐 방지
        response.setStatus(code);
        PrintWriter out = response.getWriter();
        out.print(json);
    }
}

