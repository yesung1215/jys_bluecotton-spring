package com.app.bluecotton.domain.vo.shop;

import lombok.Data;

import java.util.Date;

@Data
public class ProductReviewReportVO {
    private Long id;
    private String productReviewReportCreateAt;
    private String productReviewReportContent;
    private Long memberId;
    private Long productReviewId;
    private Date productReportCreateAt;
}

