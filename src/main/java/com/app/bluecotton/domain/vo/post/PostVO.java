package com.app.bluecotton.domain.vo.post;

import com.app.bluecotton.domain.dto.post.PostDetailDTO;
import lombok.Data;

import java.util.Date;

@Data
public class PostVO {
    private Long id;
    private String postTitle;
    private String postContent;
    private Date postCreateAt;
    private Long postReadCount;
    private boolean postStatus;
    private Long memberId;
    private Long somId;
}
