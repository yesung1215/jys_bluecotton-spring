package com.app.bluecotton.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentPrepareRequest {
    private Long orderId;
    private Long memberId;
    private Long amount;
    private String paymentType;
    private String merchantUid;
}

