package com.app.bluecotton.domain.vo.shop;

import lombok.Data;

import java.util.Date;

@Data
public class OrderVO {
    private Long id;
    private Date orderCreateAt;
    private Character orderStatus;
    private Integer orderQuantity;
    private Long orderTotalPrice;
    private Long cartId;
    private Long memberId;
    private Long productId;
}
