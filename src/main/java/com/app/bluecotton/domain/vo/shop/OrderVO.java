package com.app.bluecotton.domain.vo.shop;

import lombok.Data;

import java.util.Date;

@Data
public class OrderVO {
    private Long id;
    private Date orderCreateAt;
    private boolean orderStatus;
    private Long productId;
    private Long memberId;
}
