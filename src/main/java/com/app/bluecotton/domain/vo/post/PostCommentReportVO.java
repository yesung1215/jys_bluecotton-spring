package com.app.bluecotton.domain.vo.post;

import lombok.Data;

@Data
public class PostCommentReportVO {
    private Long id;
    private String postCommentReportContent;
    private Long postCommentId;
    private Long memberId;
}
