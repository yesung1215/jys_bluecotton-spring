package com.app.bluecotton.domain.vo.shop;

import lombok.Data;

@Data
public class DeliveryVO {
    private Long id;
    private String deliveryReceiverName;
    private String deliveryReceiverPhone;
    private String deliveryAddress;
    private String deliveryRequest;
    private Integer deliveryFee;
    private Long memberId;
    private Long productId;
    private DeliveryStatus deliveryStatus;
}