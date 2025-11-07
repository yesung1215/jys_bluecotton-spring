package com.app.bluecotton.domain.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private Long id;
    private Long userId;
    private Long productId;
    private Integer quantity;
    private String productName;
    private String productPrice;
    private String productPurchaseType;
    private Integer productStock;
}
