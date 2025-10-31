package com.app.bluecotton.domain.vo.shop;

import lombok.Data;

@Data
public class PaymentSocialVO {
    private Long id;
    private String pgProvider;
    private String pgDealNumber;
    private Long paymentId;
}
