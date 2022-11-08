package org.mapleleaf.backend.jwt;


import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapleleaf.backend.exception.LoggedOutTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.password}")
    private String secretKey;

    private final RedisTemplate redisTemplate;

    /* 토큰 생성 메소드 */
    public String createToken(String subject) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + Duration.ofDays(30).toMillis()); // 임시만료기간: 30일

        return Jwts.builder()
                .setIssuer("maple-leaf") // 토큰발급자(iss)
                .setIssuedAt(now) // 발급시간(iat)
                .setExpiration(expiration) // 만료시간(exp)
                .setSubject(subject) //  토큰 제목(subject)
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secretKey.getBytes())) // 알고리즘, 시크릿 키
                .compact();
    }

    public String createRefreshToken(String subject){
        Date now = new Date();
        Date expiration = new Date(now.getTime() + Duration.ofDays(14).toMillis()); // 만료기간: 2주

        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secretKey.getBytes()))
                .compact();
    }

    public String getUuidFromToken(String token){
        return Jwts.parser()
                .setSigningKey(Base64.getEncoder().encodeToString(secretKey.getBytes()))
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().setSigningKey(Base64.getEncoder().encodeToString(secretKey.getBytes()))
                    .parseClaimsJws(token).getBody();
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public Long getExpirationTime(String token) {
        Date expiration = Jwts.parser().setSigningKey(Base64.getEncoder().encodeToString(secretKey.getBytes()))
                .parseClaimsJws(token).getBody().getExpiration();

        Long now = new Date().getTime();
        return (expiration.getTime() - now);
    }

    public boolean isTokenLogout(String token) {
        String isLogout = (String) redisTemplate.opsForValue().get(token);
        if (StringUtils.hasText(isLogout)) throw new LoggedOutTokenException();
        return true;
    }
}
