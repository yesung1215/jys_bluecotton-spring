package com.app.bluecotton.exception;

import com.app.bluecotton.domain.dto.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // MyTest 관련 예외 처리
    @ExceptionHandler(MyTestException.class)
    public ResponseEntity<ApiResponseDTO> handleMyTestException(MyTestException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponseDTO.of(e.getMessage()));
    }

    //  Member 관련
    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleMemberException(MemberException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDTO.of(e.getMessage()));
    }

    //  Auth 관련
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleAuthException(AuthException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponseDTO.of(e.getMessage()));
    }

    //  Token 관련
    @ExceptionHandler(JwtTokenException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleTokenException(JwtTokenException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponseDTO.of(e.getMessage()));
    }

    // 모든 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleException(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponseDTO.of(e.getMessage()));
    }
}