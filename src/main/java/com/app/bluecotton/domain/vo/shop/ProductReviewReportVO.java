package com.app.bluecotton.domain.vo.shop;

import lombok.Data;

@Data
public class ProductReviewReportVO {
    private Long id;
    private String productCommentReportContent;
    private Long userId;
}
