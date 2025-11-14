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
    private Long id;
    private String postCommentContent;
    private Date postCommentCreateAt;
    private Long postCommentLikeCount;

    private String memberNickname;
    private String memberProfileUrl;

    private Long memberId;
    private Integer isCommentLiked;

    // 대댓글 포함
    private List<PostReplyDTO> replies;
}
