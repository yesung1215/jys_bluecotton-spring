package com.app.bluecotton.domain.vo.post;

import lombok.Data;

@Data
public class PostImageVO {
    private Long id;
    private String postImagePath;
    private String postImageName;
    private Long postId;
}
