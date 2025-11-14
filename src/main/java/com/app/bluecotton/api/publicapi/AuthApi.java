package com.app.bluecotton.api.publicapi;

import com.app.bluecotton.domain.dto.ApiResponseDTO;
import com.app.bluecotton.domain.dto.TokenDTO;
import com.app.bluecotton.domain.vo.member.MemberVO;
import com.app.bluecotton.service.AuthService;
import com.app.bluecotton.service.MemberService;
import com.app.bluecotton.service.SmsService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth/*")
public class AuthApi {

    private final AuthService authService;
    private final RedisTemplate redisTemplate;
    private final MemberService memberService;
    private final SmsService smsService;

    //  로그인
    @PostMapping("login")
    public ResponseEntity<ApiResponseDTO<Object>> login(@RequestBody MemberVO memberVO) {
        Map<String,String> tokens = authService.login(memberVO);

        log.info("tokens: {}", memberVO.toString());
        //  refresh 토큰은 cookie로 전달
        String refreshToken = tokens.get("refreshToken");
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true) //  *필수
//                .secure(true) https에서 사용
                .path("/") //   모든 경로에 쿠키 전송사용
                .maxAge(60 * 60 * 24 * 7)
                .build();

        tokens.remove("refreshToken");

        //  access 토큰은 그대로 발급
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookie.toString()) //   쿠키를 헤더에 심는다
                .body(ApiResponseDTO.of("로그인이 성공했습니다", tokens));
    }

    //  토큰 재발급
    @PostMapping("refresh")
    public ResponseEntity<ApiResponseDTO<Object>> refresh(@CookieValue("refreshToken") String refreshToken, @RequestBody TokenDTO tokenDTO) {
        Map<String, String> response = new HashMap<>();
        tokenDTO.setRefreshToken((refreshToken));
        String newAccessToken = authService.reissueAccessToken(tokenDTO);
        response.put("accessToken", newAccessToken);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("토큰이 재발급 되었습니다",response));
    }

    //  키를 교환
    @GetMapping("/oauth2/success")
    public ResponseEntity<ApiResponseDTO<Object>> oauth2Success(@RequestParam("key") String key) {
        Map<String, String> tokens = redisTemplate.opsForHash().entries(key);
        if(tokens==null || tokens.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponseDTO.of("요효시간 만료", null));
        }
        return  ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("로그인 성공", tokens));
    }

    @PostMapping("find-email")
    public ResponseEntity<ApiResponseDTO<Object>> findEmail(@RequestParam String memberName, @RequestParam String memberPhone) {
        log.info("memberName: {}, memberPhone: {}", memberName, memberPhone);
        String email = memberService.getMemberEmailByNameAndPhone(memberName, memberPhone);
        Map<String, String> result = new HashMap<>();
        result.put("email", email);

        return  ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("이메일 찾기 성공", result));
    }

    @PostMapping("reset-password")
    public ResponseEntity<ApiResponseDTO<Object>> resetPassword(@RequestParam String memberEmail, @RequestParam String newPassword) {
        memberService.resetPassword(memberEmail, newPassword);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("비밀번호 재설정 완료"));
    }

    // 이메일로 인증코드 전송
    @PostMapping("/codes/email")
    public ResponseEntity<ApiResponseDTO> sendAuthentificationCodeByEmail(String toEmail, HttpSession session) {
        ApiResponseDTO response = smsService.sendAuthentificationCodeByEmail(toEmail, session);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 인증코드 확인
    @PostMapping("/codes/verify")
    public ResponseEntity<ApiResponseDTO> verifyAuthentificationCode(String userAuthentificationCode, HttpSession session) {
        String authentificationCode = (String) session.getAttribute("authentificationCode");
        boolean isVerified = authentificationCode != null && authentificationCode.equals(userAuthentificationCode);

        Map<String, Boolean> verified = new HashMap<>();
        verified.put("verified", isVerified);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponseDTO.of("인증코드 확인 완료", verified));

    }

}
