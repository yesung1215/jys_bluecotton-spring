package com.app.bluecotton.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MyReviewListDTO {

    private Long id;
    private Long productId;
    private String productName;
    private String productReviewDate;
    private String productReviewContent;
    private Integer productReviewRating;
    private String productImageUrl;

}
