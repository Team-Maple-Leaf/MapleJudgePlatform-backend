package org.mapleleaf.backend.service;

import lombok.extern.slf4j.Slf4j;
import org.mapleleaf.backend.dto.LoginRequestDto;
import org.mapleleaf.backend.entity.Member;
import org.mapleleaf.backend.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@Service
public class LoginService {

    @Autowired
    private MemberRepository memberRepository;

    public boolean isValidRequest(LoginRequestDto dto) {
        return dto.getLeaf().equals(encryption(dto.getMaple(), dto.getName(), dto.getPicture()));
    }

    public boolean memberExist(String maple) {
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
