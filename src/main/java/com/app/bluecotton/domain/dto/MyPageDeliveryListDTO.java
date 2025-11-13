package com.app.bluecotton.domain.dto;

import com.app.bluecotton.domain.vo.shop.DeliveryStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class MyPageDeliveryListDTO {

    private Long orderId;
    private Long deliveryId;
    private Long productId;
    private String productName;
    private String orderCreateAt;
    private DeliveryStatus deliveryStatus;
    private String productMainImageUrl;

}
