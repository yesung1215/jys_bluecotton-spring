package com.app.bluecotton.domain.vo.post;

import lombok.Data;

@Data
public class PostReplyLikeVO {
    private Long id;
    private Long postReplyId;
    private Long memberId;
}
