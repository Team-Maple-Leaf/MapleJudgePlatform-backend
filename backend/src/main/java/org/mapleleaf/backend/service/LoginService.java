package org.mapleleaf.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapleleaf.backend.dto.LoginRequestDto;
import org.mapleleaf.backend.dto.TokenDto;
import org.mapleleaf.backend.entity.Member;
import org.mapleleaf.backend.exception.UnauthorizedException;
import org.mapleleaf.backend.jwt.JwtProvider;
import org.mapleleaf.backend.repository.MemberRepository;
import org.springframework.stereotype.Service;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    public TokenDto checkRequestAndCreateToken(LoginRequestDto loginRequestDto){
        // 유효성 검사
        if (isValidRequest(loginRequestDto)) {
            // 존재하지 않는 멤버면 join 먼저 진행
            if (!memberExists(loginRequestDto.getMaple())) {
                join(loginRequestDto);
            }
            String uuid = getUUID(loginRequestDto.getMaple());
            String token = jwtProvider.createToken(uuid); // 토큰 생성

            return new TokenDto(token);
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
        byte[] mapleBytes = maple.getBytes();
        byte[] nameBytes = name.getBytes();
        byte[] pictureBytes = picture.getBytes();

        // xor
        byte[] xorResult = xor(pictureBytes, xor(mapleBytes, nameBytes));
        log.info("xor - {}", xorResult);

        // sha-256, base64 인코딩
        MessageDigest md;
        Base64.Encoder encoder;

        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(xorResult);
            encoder = Base64.getEncoder();

            // TODO : md.digest() 다시 확인
            log.info("result - {}", encoder.encodeToString(md.digest()));
            return encoder.encodeToString(md.digest());
        } catch (Exception e) {
            log.error(e.getMessage());
            return e.getMessage();
        }
    }

    public byte[] xor(byte[] bytes1, byte[] bytes2) {
        int bigger_length = Math.max(bytes1.length, bytes2.length);
        byte[] ret = new byte[bigger_length];

        for (int i = 0; i < bigger_length; i++)
            ret[i] = (byte)(bytes1[i % bytes1.length] ^ bytes2[i % bytes2.length]);
        return ret;
    }
}
