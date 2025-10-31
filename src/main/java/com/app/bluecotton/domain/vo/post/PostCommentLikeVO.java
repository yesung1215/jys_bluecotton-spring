package com.app.bluecotton.domain.vo.post;

import lombok.Data;

@Data
public class PostCommentLikeVO {
    private Long id;
    private Long userId;
    private Long commentId;
}
