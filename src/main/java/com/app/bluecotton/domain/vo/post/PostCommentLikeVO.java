package com.app.bluecotton.domain.vo.post;

import lombok.Data;

@Data
public class PostCommentLikeVO {
    private Long id;
    private Long memberId;
    private Long postCommentId;
}
