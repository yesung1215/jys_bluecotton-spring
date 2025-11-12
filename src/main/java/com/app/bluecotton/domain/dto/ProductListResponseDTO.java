package com.app.bluecotton.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductListResponseDTO {

    private Long id;
    private String productName;
    private String productType;
    private Integer productPrice;
    private String productPurchaseType;
    private Double productAvgRating;
    private Integer productReviewCount;
    private Integer productLikeCount;
    private String productImageUrl;
    private Integer isLiked;

}
