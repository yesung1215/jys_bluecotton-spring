package com.app.bluecotton.domain.vo.shop;

import lombok.Data;

import java.util.Date;

@Data
public class PaymentVO {
    private Long id;
    private Integer paymentPrice;
    private String paymentType;
    private PaymentStatus paymentStatus;
    private Date paymentCreateAt;
    private Long orderId;
}


