package org.mapleleaf.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapleleaf.backend.dto.LoginRequestDto;
import org.mapleleaf.backend.dto.TokenDto;
import org.mapleleaf.backend.entity.Member;
import org.mapleleaf.backend.exception.LoggedOutTokenException;
import org.mapleleaf.backend.exception.UnauthorizedException;
import org.mapleleaf.backend.jwt.JwtProvider;
import org.mapleleaf.backend.repository.MemberRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtProvider jwtProvider;
    private final RedisTemplate redisTemplate;
    private final MemberRepository memberRepository;

    // 로그인 요청 시 실행하는 메서드
    public TokenDto checkRequestAndCreateToken(LoginRequestDto loginRequestDto){
        // 유효성 검사
        if (isValidRequest(loginRequestDto)) {
            // 존재하지 않는 멤버면 join 먼저 진행
            if (!memberExists(loginRequestDto.getMaple())) {
                join(loginRequestDto);
            }
            String uuid = getUUID(loginRequestDto.getMaple());

            String accessToken = jwtProvider.createToken(uuid);
            String refreshToken = jwtProvider.createRefreshToken(uuid);
            redisTemplate.opsForValue().set(accessToken, uuid, jwtProvider.getExpirationTime(refreshToken), TimeUnit.MILLISECONDS);
            redisTemplate.opsForValue().set(accessToken, refreshToken, jwtProvider.getExpirationTime(refreshToken), TimeUnit.MILLISECONDS);

            return TokenDto.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        }

        throw new UnauthorizedException();
    }

    public boolean memberExists(String maple) {
        return memberRepository.existsByMaple(maple);
    }

    public void join(LoginRequestDto loginRequestDto) {
        memberRepository.save(Member.builder()
                .maple(loginRequestDto.getMaple())
                .uuid(UUID.randomUUID().toString())
                .name(loginRequestDto.getName())
                .build());
    }

    public String getUUID(String maple){
        return memberRepository.findByMaple(maple).getUuid();
    }

    public boolean isValidRequest(LoginRequestDto dto) {
        return dto.getLeaf().equals(encryption(dto.getMaple(), dto.getName(), dto.getPicture()));
    }

    // 유효성 검사
    public String encryption(String maple, String name, String picture) {
        final Charset ENCODE = StandardCharsets.UTF_8;
        byte[] mapleBytes;
        byte[] nameBytes;
        byte[] pictureBytes;
        mapleBytes = maple.getBytes(ENCODE);
        nameBytes = name.getBytes(ENCODE);
        pictureBytes = picture.getBytes(ENCODE);
        int totalLength = mapleBytes.length + nameBytes.length + pictureBytes.length;
        byte[] result = new byte[totalLength];

        System.arraycopy(mapleBytes, 0, result, 0, mapleBytes.length);
        System.arraycopy(nameBytes, 0, result, mapleBytes.length, nameBytes.length);
        System.arraycopy(pictureBytes, 0, result, mapleBytes.length + nameBytes.length, pictureBytes.length);
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(getChecksum(result));
    }

    private byte[] getChecksum(byte[] bytes) {
        byte ret = 0;
        for (byte aByte : bytes)
            ret = (byte)(aByte ^ ret);
        return String.valueOf(ret & 0xff).getBytes();
    }

    public List<Object> logout(String bearerToken) {

        String token = bearerToken.substring("Bearer ".length());
        // token 검증
        if (jwtProvider.isTokenValid(token)) {
            Long expiration = jwtProvider.getExpirationTime(token);
            redisTemplate.opsForValue().set(token, "logout", expiration, TimeUnit.MILLISECONDS);
            return Collections.emptyList();
        }
        throw new LoggedOutTokenException();
    }

    public TokenDto refresh(TokenDto tokenDto){
        // 1. accToken 만료?
        // 2. refToken 만료?
        String uuid = String.valueOf(redisTemplate.opsForValue().get(tokenDto.getAccessToken()));
        return TokenDto.builder()
                .accessToken(jwtProvider.createToken(uuid))
                .refreshToken(jwtProvider.createRefreshToken(uuid))
                .build();
    }
}
