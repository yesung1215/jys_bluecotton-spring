package com.app.bluecotton.domain.dto;

import lombok.Data;

@Data
public class PortOneDTO {
    private int code;
    private String message;
    private Response response;

    @Data
    public static class Response{
        private String impUid;
        private String merchantUid;
        private Long amount;
        private String status;
        private String pgProvider;
        private String payMethod;
        private String currency;
        private Card card;
    }

    @Data
    public static class Card{
        private String issuerCode;
        private String number;
        private String cardType;
    }
}
