package com.app.bluecotton.domain.vo.post;

import lombok.Data;

import java.util.Date;

@Data
public class PostReplyVO {
    private Long id;
    private String postReplyContent;
    private Date postReplyCreateAt;
    private Long postCommentId;
    private Long memberId;
}
