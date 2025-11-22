package com.app.bluecotton.service;

import com.app.bluecotton.domain.dto.TokenDTO;
import com.app.bluecotton.domain.vo.member.MemberVO;
import com.app.bluecotton.exception.JwtTokenException;
import com.app.bluecotton.exception.MemberException;
import com.app.bluecotton.repository.MemberDAO;
import com.app.bluecotton.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class AuthServiceImpl implements AuthService {

    @Value("${jwt.token-blacklist-prefix}")
    private String BLACKLIST_TOKEN_PREFIX;

    @Value("${jwt.refresh-blacklist-prefix}")
    private String REFRESH_TOKEN_PREFIX;

    private final MemberDAO memberDAO;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate redisTemplate;

    @Override
    public Map<String, String> login(MemberVO memberVO) {

        Map<String, String> claim = new HashMap<>();
        Map<String,String> tokens = new HashMap<>();

        // 1. 아이디 확인
        if(!memberDAO.existsByMemberEmail(memberVO.getMemberEmail())) {
            throw new MemberException("아이디를 확인해주세요");
        }

        // 2. 비밀번호 확인
        Long memberId = memberDAO.findIdByMemberEmail(memberVO.getMemberEmail());
        MemberVO foundMember = memberDAO.findById(memberId)
                .orElseThrow(() -> new MemberException("회원이 없습니다"));

        if(foundMember.getMemberEmail().equals("test123@gmail.com")) {

            if(!memberVO.getMemberPassword().equals(foundMember.getMemberPassword())) {
                throw new MemberException("비밀번호를 확인해주세요.");
            }

        } else {
            if(!passwordEncoder.matches(memberVO.getMemberPassword(), foundMember.getMemberPassword())) {
                throw new MemberException("비밀번호를 확인해주세요.");
            }
        }


        // 3. 토큰 생성
        claim.put("memberEmail", memberVO.getMemberEmail());
        String accessToken = jwtTokenUtil.generateAccessToken(claim);
        String refreshToken = jwtTokenUtil.generateRefreshToken(claim);

        // 4. 토큰을 Redis에 저장
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setMemberId(foundMember.getId());
        tokenDTO.setRefreshToken(refreshToken);
        tokenDTO.setAccessToken(accessToken);
        saveRefreshToken(tokenDTO);

        // 5. 화면에 반환
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        return tokens;
    }

    @Override
    public boolean saveRefreshToken(TokenDTO tokenDTO) {
        Long id = tokenDTO.getMemberId();
        String refreshToken = tokenDTO.getRefreshToken();

        try {
            String key = REFRESH_TOKEN_PREFIX + id;
            redisTemplate.opsForValue().set(key, refreshToken, 7, TimeUnit.DAYS);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean validateRefreshToken(TokenDTO tokenDTO) {
        Long id = tokenDTO.getMemberId();
        String refreshToken = tokenDTO.getRefreshToken();

        String key = REFRESH_TOKEN_PREFIX + id;
        try {
            String storedToken = redisTemplate.opsForValue().get(key).toString();

            if(!refreshToken.equals(storedToken)) {
                return false;
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String reissueAccessToken(TokenDTO tokenDTO) {
        Map<String,String> claim = new HashMap<>();

        String memberEmail = (String)jwtTokenUtil.getMemberEmailFromToken(tokenDTO.getRefreshToken()).get("memberEmail");

        Long id =  memberDAO.findIdByMemberEmail(memberEmail);

        tokenDTO.setMemberId(id);

        // 1. 기존 RefreshToken 또는 AccessToken 블랙리스트인지 확인
        if(isBlacklistedRefreshToken(tokenDTO)) {
            throw new JwtTokenException("이미 로그아웃된 토큰입니다. 다시 로그인하세요");
        }

        // 2. 리프레쉬 토큰 검증
        if(!validateRefreshToken(tokenDTO)) {
            throw new JwtTokenException("Refresh Token이 유효하지 않습니다. 다시 로그인하세요");
        }

        // 3. Member 정보 조회
        MemberVO mebmerVO = memberDAO.findById(id).orElseThrow(() -> new MemberException("회원 정보를 찾을 수 없습니다"));
        claim.put("memberEmail", mebmerVO.getMemberEmail());
        String newAccessToken = jwtTokenUtil.generateAccessToken(claim);
        return newAccessToken;
    }

    @Override
    public boolean revokeRefreshToken(TokenDTO tokenDTO) {
        Long id = tokenDTO.getMemberId();
        String refreshToken = tokenDTO.getRefreshToken();
        String key = REFRESH_TOKEN_PREFIX + id;

        try {
            String storedToken = redisTemplate.opsForValue().get(key).toString();
            if(storedToken != null && !storedToken.equals(refreshToken)) {
                // 삭제
                redisTemplate.delete(key);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    // 탈취 방어
    @Override
    public boolean saveBlacklistedToken(TokenDTO tokenDTO) {
        String memberEmail = (String) jwtTokenUtil.getMemberEmailFromToken(tokenDTO.getRefreshToken()).get("memberEmail");
        Long id =  memberDAO.findIdByMemberEmail(memberEmail);
        String refreshToken = tokenDTO.getRefreshToken();
        String key = BLACKLIST_TOKEN_PREFIX + id;

        try {
            redisTemplate.opsForSet().add(key, refreshToken);
//        TTL 설정
            redisTemplate.expire(key, 7, TimeUnit.DAYS);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean isBlacklistedRefreshToken(TokenDTO tokenDTO) {
        Long id = tokenDTO.getMemberId();
        String refreshToken = tokenDTO.getRefreshToken();
        String key = BLACKLIST_TOKEN_PREFIX + id;

        try {
            Boolean isMember = redisTemplate.opsForSet().isMember(key, refreshToken);
            return isMember != null && isMember;
        } catch (Exception e) {
            return false;
        }
    }
}
