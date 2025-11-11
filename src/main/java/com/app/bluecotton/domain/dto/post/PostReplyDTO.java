package com.app.bluecotton.domain.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostReplyDTO {
    private Long replyId;
    private String replyContent;
    private Date replyCreateAt;
    private Long replyLikeCount;

    private Long memberId;
    private String memberNickname;
    private String memberProfileUrl;

}
