package com.app.bluecotton.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MyTestException extends RuntimeException {
    public MyTestException(String message) {
        super(message);
    }
}
