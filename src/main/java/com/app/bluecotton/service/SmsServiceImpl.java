package com.app.bluecotton.service;

import com.app.bluecotton.domain.dto.ApiResponseDTO;
import com.app.bluecotton.util.SmsUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmsServiceImpl implements SmsService {

    private final SmsUtil smsUtil;

    @Override
    public ApiResponseDTO sendAuthentificationCodeByEmail(String toEmail, HttpSession session) {
        // 세션에 랜덤 코드를 생성해서 저장
        String AuthentificationCode = smsUtil.saveAuthentificationCode(session);
        // 이메일 전송
        smsUtil.sendEmail(toEmail, AuthentificationCode);
        return ApiResponseDTO.of("메세지 전송 성공");
    }
}
