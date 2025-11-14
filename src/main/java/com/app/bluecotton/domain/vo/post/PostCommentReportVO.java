package com.app.bluecotton.domain.vo.post;

import lombok.Data;

import java.util.Date;

@Data
public class PostCommentReportVO {
    private Long id;
    private String postCommentReportContent;
    private Long postCommentId;
    private Long memberId;
    private Date postCommentReportCreateAt;
}
