package com.app.bluecotton.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private Long memberId;
    private Long productId;
//    private Integer productPrice;
    private Date orderCreateAt;
    private Character orderStatus;
    private Integer orderQuantity;
    private Long orderTotalPrice;
}
