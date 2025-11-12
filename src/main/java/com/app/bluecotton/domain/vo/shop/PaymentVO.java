package com.app.bluecotton.domain.vo.shop;

import lombok.Data;

import java.util.Date;

@Data
public class PaymentVO {
    private Long id;
    private Long memberId;
    private Integer paymentPrice;
    private String paymentType;
    private PaymentStatus paymentStatus;
    private Date paymentCreateAt;
    private Long orderId;

    private String merchantUid;
    private String impUid;
    private Long paidAmount;
}


