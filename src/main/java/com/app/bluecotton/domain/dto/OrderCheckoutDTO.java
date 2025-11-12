package com.app.bluecotton.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCheckoutDTO {
    private Long memberId;
    private Long id;
    private List<OrderItemDTO> items;
}
