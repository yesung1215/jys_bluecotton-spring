package com.app.bluecotton.domain.vo.shop;

import lombok.Data;

@Data
public class ProductImageVO {
    private Long id;
    private String productImagePath;
    private Long productId;
}
