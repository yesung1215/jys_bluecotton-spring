package com.app.bluecotton.domain.vo.post;

import lombok.Data;

@Data
public class PostReportVO {
    private Long id;
    private String postReportContent;
    private Long memberId;
    private Long postId;
}
