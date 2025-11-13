package com.app.bluecotton.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDTO {
    private Long id;
    private Date orderCreateAt;
    private Character orderStatus;
    private Long memberId;
    private Long productId;
    private Integer orderQuantity;
    private Long orderTotalPrice;
    private String productName;
    private Long productPrice;
    private String purchaseType;
}
