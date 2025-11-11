package com.app.bluecotton.domain.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCommentDTO {
    private Long commentId;
    private String commentContent;
    private Date commentCreateAt;
    private Long commentLikeCount;

    private String memberNickname;
    private String memberProfileUrl;

    private Long memberId;

    // 대댓글 포함
    private List<PostReplyDTO> replies;
}
