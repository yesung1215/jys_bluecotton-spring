package com.app.bluecotton.domain.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostReplyDTO {
    private Long id;
    private String postReplyContent;
    private Date postReplyCreateAt;
    private Long postReplyLikeCount;

    private Long memberId;
    private String memberNickname;
    private String memberProfileUrl;

    private Integer isReplyLiked;

}
