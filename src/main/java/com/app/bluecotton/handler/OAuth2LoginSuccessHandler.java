package com.app.bluecotton.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    // 소셜로그인 인가된 데이터가 들어온다.
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if(authentication instanceof OAuth2AuthenticationToken authToken){
            OAuth2User user = authToken.getPrincipal();
            Map<String, Object> attributes = user.getAttributes();
            // naver, google, kakao
            String memberProvider = authToken.getAuthorizedClientRegistrationId();
            String memberEmail = null;
            String memberName = null;
            String memberSocialProviderId = null;
            Long memberId = null;
            Map<String, String> tokens = null;

            log.info("user: {}", user);
            // 1. 어디로 들어왔는지를 확인
            if(memberProvider.equals("google")){
                memberEmail = (String)attributes.get("email");
                memberName = (String)attributes.get("name");
                memberSocialProviderId = (String)attributes.get("sub");
            } else if(memberProvider.equals("naver")){
                memberEmail = (String)attributes.get("email");
                memberName = (String)attributes.get("name");
                memberSocialProviderId = (String)attributes.get("id");
            } else if(memberProvider.equals("kakao")){
                memberEmail = (String)attributes.get("email");
                memberName = (String)attributes.get("nickname");
                memberSocialProviderId = (String)attributes.get("id");
            }

            // 2. 이미 회원가입이 되어있는지
            // 3. 어디로 접속했는지 확인!
            // 4. 이미 회원가입이라면 토큰 발급
            // 4. 신규 회원가입 후 토큰 발급
            // 5. redis로 교환하기 위한 key를 등록
            // 6. redis에 refresh 토큰을 등록 (검증)
            // 7. 쿠키에 심는다
        }
    }
}
























