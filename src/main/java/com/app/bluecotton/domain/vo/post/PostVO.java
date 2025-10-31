package com.app.bluecotton.domain.vo.post;

import lombok.Data;

import java.util.Date;

@Data
public class PostVO {
    private Long id;
    private String postTitle;
    private String postContent;
    private Date postTime;
    private Long postCountRead;
    private boolean postStatus;
    private Long userId;
    private Long commentId;
    private Long somId;
}
