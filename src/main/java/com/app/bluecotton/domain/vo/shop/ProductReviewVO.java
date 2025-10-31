package com.app.bluecotton.domain.vo.shop;

import lombok.Data;

import java.util.Date;

@Data
public class ProductReviewVO {
    private Long id;
    private Date productReviewDate;
    private double productReviewRating;
    private String productReviewContent;
    private Long productId;
    private Long userId;
    private Long productReportId;
}
