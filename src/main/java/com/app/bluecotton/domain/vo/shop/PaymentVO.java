package com.app.bluecotton.domain.vo.shop;

import lombok.Data;

import java.util.Date;

@Data
public class PaymentVO {
    private Long id;
    private Integer paymentPrice;
    private String paymentType;
    private Enum paymentStatus;
    private Date paymentDate;
    private Long orderId;
}
