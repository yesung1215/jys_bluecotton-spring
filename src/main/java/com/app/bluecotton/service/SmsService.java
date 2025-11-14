package com.app.bluecotton.service;

import com.app.bluecotton.domain.dto.ApiResponseDTO;
import jakarta.servlet.http.HttpSession;

public interface SmsService {

    public ApiResponseDTO sendAuthentificationCodeByEmail(String toEmail, HttpSession session);
}
