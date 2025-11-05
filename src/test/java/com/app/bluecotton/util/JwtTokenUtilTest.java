package com.app.bluecotton.util;

import com.app.bluecotton.domain.vo.member.MemberVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class JwtTokenUtilTest {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Test
    public void generateToken() {
        Map<String, String> claims = new HashMap<>();
        claims.put("memberEmail", "test123@gmail.com");
        String token = jwtTokenUtil.generateRefreshToken(claims);
        log.info("claims: {}", jwtTokenUtil.getMemberEmailFromToken(token));
    }
}