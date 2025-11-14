package com.app.bluecotton.domain.dto.post;

import com.app.bluecotton.domain.vo.post.PostVO;
import lombok.Data;
import java.util.List;

@Data
public class PostWriteDTO {
    private String postTitle;
    private String postContent;
    private Long memberId;
    private Long somId;
    private List<Long> postImageIds;
    private Long draftId;

    public PostVO postVO() {
        PostVO postVO = new PostVO();
        postVO.setPostTitle(postTitle);
        postVO.setPostContent(postContent);
        postVO.setMemberId(memberId);
        postVO.setSomId(somId);
        return postVO;
    }
}
