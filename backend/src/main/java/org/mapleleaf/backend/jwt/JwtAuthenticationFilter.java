package org.mapleleaf.backend.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // JWT 토큰 인증 과정을 위한 Filter
    // spring security에서는 기본적으로 토큰 처리를 위한 필터가 없으므로
    // 구현을 통해 Filter Chain에 추가해야한다.

    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = getTokenFromRequest(request); // Request에서 토큰 값 꺼내기
            StringUtils.hasText(null);

            if(StringUtils.hasText(token) && jwtProvider.isTokenValid(token)) {
                String uuid = jwtProvider.getUuidFromToken(token); // 토큰에서 uuid 꺼낸다.
                UserAuthentication authentication = new UserAuthentication(uuid, null, null);  // id를 인증한다.
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (MalformedJwtException e) {
            log.error("JwtAuthenticationFilter::: Malformed");
            request.setAttribute("exception", "MalformedJwtException");
        } catch (ExpiredJwtException e) {
            log.error("JwtAuthenticationFilter::: Expired");
            request.setAttribute("exception", "ExpiredJwtException");
        } catch (UnsupportedJwtException e) {
            log.error("JwtAuthenticationFilter::: Unsupported");
            request.setAttribute("exception", "UnsupportedJwtException");
        } catch (SignatureException e) {
            log.error("JwtAuthenticationFilter::: Signature");
            request.setAttribute("exception", "SignatureException");
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader(HEADER_STRING);
        if(StringUtils.hasText(bearerToken)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
