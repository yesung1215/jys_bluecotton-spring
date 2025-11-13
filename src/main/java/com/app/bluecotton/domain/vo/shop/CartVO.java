package com.app.bluecotton.domain.vo.shop;

import lombok.Data;

@Data
public class CartVO {
    private Long id;
    private Long memberId;
    private Long productId;
    private Integer cartQuantity;
}
