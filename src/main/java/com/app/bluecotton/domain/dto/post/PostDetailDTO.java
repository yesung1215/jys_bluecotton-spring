package com.app.bluecotton.domain.dto.post;

import com.app.bluecotton.domain.dto.MemberResponseDTO;
import com.app.bluecotton.domain.dto.post.PostCommentDTO;
import com.app.bluecotton.domain.vo.post.PostVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDetailDTO {
    private Long id;
    private String postTitle;
    private String postContent;
    private String postImageUrl;
    private Date postCreateAt;
    private Long postReadCount;

    private String memberNickname;
    private String memberProfileUrl;
    private Long memberId;

    private Long postLikeCount;
    private Integer postIsLike;

    private List<PostCommentDTO> comments;

}
