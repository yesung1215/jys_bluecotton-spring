package com.app.bluecotton.domain.vo.shop;

import lombok.Data;

@Data
public class ProductVO {
    private Long id;
    private String productName;
    private Integer productPrice;
    private Integer productStock;
    private Enum productCategory;
    private Enum productType;
    private Enum productPurchaseType;
}
