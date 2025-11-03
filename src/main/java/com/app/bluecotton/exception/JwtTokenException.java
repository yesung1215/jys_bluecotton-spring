package com.app.bluecotton.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class JwtTokenException extends RuntimeException {
    public JwtTokenException(String message) {
        super(message);
    }
}