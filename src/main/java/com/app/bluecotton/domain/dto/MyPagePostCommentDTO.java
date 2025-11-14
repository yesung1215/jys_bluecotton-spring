package com.app.bluecotton.domain.dto;

import com.app.bluecotton.domain.vo.post.PostCommentVO;
import com.app.bluecotton.domain.vo.post.PostReplyVO;
import com.app.bluecotton.domain.vo.post.PostVO;
import com.app.bluecotton.domain.vo.som.SomVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyPagePostCommentDTO {
//    TBPC.ID                AS COMMENT_ID,
//    TBPR.ID                AS REPLY_ID,
//    TBP.ID
//    TBPC.POST_COMMENT_CREATE_AT,
//    TBPR.POST_REPLY_CREATE_AT,
//    TBP.POST_TITLE,
//    TBS.SOM_CATEGORY

    private Long commentId;
    private Long replyId;
    private Long postId;
    private String postCommentContent;
    private String postReplyContent;
    private Date postCommentCreateAt;
    private Date postReplyCreateAt;
    private String postTitle;
    private String somCategory;

    public MyPagePostCommentDTO(PostCommentVO postCommentVO, PostReplyVO postReplyVO, PostVO postVO, SomVO somVO) {
        this.commentId = postCommentVO.getId();
        this.replyId = postReplyVO.getId();
        this.postId = postVO.getId();
        this.postCommentContent = postCommentVO.getPostCommentContent();
        this.postReplyContent = postReplyVO.getPostReplyContent();
        this.postCommentCreateAt = postCommentVO.getPostCommentCreateAt();
        this.postReplyCreateAt = postReplyVO.getPostReplyCreateAt();
        this.postTitle = postVO.getPostTitle();
        this.somCategory = somVO.getSomCategory();
    }
}
