package com.app.bluecotton.handler;

import com.app.bluecotton.domain.dto.MemberResponseDTO;
import com.app.bluecotton.domain.dto.TokenDTO;
import com.app.bluecotton.domain.vo.member.MemberInsertSocialVO;
import com.app.bluecotton.domain.vo.member.MemberSocialVO;
import com.app.bluecotton.service.AuthService;
import com.app.bluecotton.service.MemberService;
import com.app.bluecotton.service.MemberSocialService;
import com.app.bluecotton.util.JwtTokenUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final MemberService memberService;
    private final MemberSocialService memberSocialService;
    private final JwtTokenUtil jwtTokenUtil;
    private final RedisTemplate redisTemplate;
    private final AuthService authService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        if (!(authentication instanceof OAuth2AuthenticationToken authToken)) return;

        OAuth2User user = authToken.getPrincipal();
        Map<String, Object> attributes = user.getAttributes();

        String provider = authToken.getAuthorizedClientRegistrationId(); // google/naver/kakao
        String email = null;
        String name = null;
        String providerId = null;
        Long memberId;
        Map<String, String> tokens;

        // 파싱
        switch (provider) {
            case "google" -> {
                email = (String) attributes.get("email");
                name = (String) attributes.get("name");
                providerId = (String) attributes.get("sub");
            }
            case "naver" -> {
                Map<String, Object> naver = (Map<String, Object>) attributes.get("response");
                email = (String) naver.get("email");
                name = (String) naver.get("name");
                providerId = (String) naver.get("id");
            }
            case "kakao" -> {
                Map<String, Object> kakaoAcc = (Map<String, Object>) attributes.get("kakao_account");
                Map<String, Object> kakaoProf = (Map<String, Object>) kakaoAcc.get("profile");
                email = (String) kakaoAcc.get("email");
                name = (String) kakaoProf.get("nickname");
                providerId = String.valueOf(attributes.get("id"));
            }
        }

        log.info("소셜 로그인 정보 email={}, provider={}", email, provider);

        // 기존 회원인지 확인
        if (memberService.existsByMemberEmail(email)) {
            memberId = memberService.getMemberIdByMemberEmail(email);
            MemberResponseDTO foundMember = memberService.getMemberById(memberId);

            List<String> providers = memberSocialService.findAllProvidersById(memberId);
            boolean existsProvider = providers.contains(provider);

            // rovider 다르면 → 자동 연동
            if (!existsProvider) {
                MemberSocialVO social = new MemberSocialVO();
                social.setMemberId(memberId);
                social.setMemberSocialProvider(provider);
                social.setMemberSocialProviderId(providerId);
                memberSocialService.registerMemberSocial(social);

                log.info("새 소셜 계정 자동 연동 성공: {} -> {}", email, provider);
            }

            // 토큰 생성
            Map<String, String> claim = Map.of("memberEmail", foundMember.getMemberEmail());
            String accessToken = jwtTokenUtil.generateAccessToken(claim);
            String refreshToken = jwtTokenUtil.generateRefreshToken(claim);

            tokens = new HashMap<>();
            tokens.put("accessToken", accessToken);
            tokens.put("refreshToken", refreshToken);

        } else {
            // 신규 회원이면 회원 저장 + 소셜 저장
            MemberInsertSocialVO newMember = new MemberInsertSocialVO();
            newMember.setMemberEmail(email);
            newMember.setMemberName(name);
            newMember.setMemberProvider(provider);

            MemberSocialVO social = new MemberSocialVO();
            social.setMemberSocialProvider(provider);
            social.setMemberSocialProviderId(providerId);

            tokens = memberService.registerSocial(newMember, social);
            memberId = memberService.getMemberIdByMemberEmail(email);

            log.info("신규 소셜 회원가입 완료: {}", email);
        }

        // refresh token 분리 저장
        String refreshToken = tokens.get("refreshToken");
        tokens.remove("refreshToken");

        // Redis 저장 (access token)
        String key = UUID.randomUUID().toString();
        redisTemplate.opsForHash().putAll(key, tokens);
        redisTemplate.expire(key, 5, TimeUnit.MINUTES);

        // refresh token redis에 저장
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setMemberId(memberId);
        tokenDTO.setRefreshToken(refreshToken);
        authService.saveRefreshToken(tokenDTO);

        // 쿠키 설정
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .path("/")
                .maxAge(60 * 60 * 24 * 7)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        //  최종 로그인 성공 redirect
        String redirectUrl = "http://localhost:3000/oauth2/success?key=" + key;
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);

        log.info("소셜 로그인 성공 redirect: {}", redirectUrl);
    }
}
























