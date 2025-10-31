package com.app.bluecotton.domain.vo.post;

import lombok.Data;

import java.util.Date;

@Data
public class PostViewVO {
    private Long id;
    private Date postViewTime;
    private Long postId;
    private Long userId;
}
