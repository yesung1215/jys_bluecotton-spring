package com.app.bluecotton.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class MyPageOrderListDTO {

    private Long   orderId;
    private Long   productId;
    private String productName;
    private String productMainImageUrl;
    private String orderCreateAt;
    private Integer orderTotalCount;
}
