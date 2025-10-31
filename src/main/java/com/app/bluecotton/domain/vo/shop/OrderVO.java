package com.app.bluecotton.domain.vo.shop;

import lombok.Data;

import java.util.Date;

@Data
public class OrderVO {
    private Long id;
    private Date orderDate;
    private String orderStatus;
    private Long productId;
    private Long userId;
}
