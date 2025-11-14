package com.app.bluecotton.util;

import jakarta.annotation.PostConstruct;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SmsUtil {

    @Value("${spring.mail.username}")
    private String emailUsername;

    @Value("${spring.mail.password}")
    private String emailPassword;

    private final JavaMailSenderImpl mailSender;

    // 인증코드 8자리 전송 및 Session에 저장
    public String saveAuthentificationCode(HttpSession session){
        String authentificationCode = RandomStringUtils.randomAlphanumeric(8);
        session.setAttribute("authentificationCode", authentificationCode);
        return authentificationCode;
    }

    // 전달받은 인증코드를 이메일로 전송
    public void sendEmail(String toEmail, String verificationCode){
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            helper.setTo(toEmail);
            helper.setSubject("[blue cotton] 인증코드 발송 이메일입니다.");
            helper.setText("[blue cotton]\n 아래의 인증코드를 입력해주세요\n" + verificationCode, true); // HTML 가능
            helper.setFrom("yunhanmin@gmail.com", "한민");

            mailSender.send(mimeMessage);

        } catch (Exception e) {
            throw new RuntimeException("메일 전송 실패: " + e.getMessage());
        }
    }


}
