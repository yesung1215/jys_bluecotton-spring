package com.app.bluecotton.domain.vo.post;

import lombok.Data;

@Data
public class PostReportVO {
    private Long id;
    private String postCommentReportContent;
    private Long userId;
    private Long postId;
}
