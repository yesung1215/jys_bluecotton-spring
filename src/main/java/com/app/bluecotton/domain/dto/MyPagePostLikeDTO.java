package com.app.bluecotton.domain.dto;

import com.app.bluecotton.domain.vo.post.PostVO;
import com.app.bluecotton.domain.vo.som.SomVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyPagePostLikeDTO {
//    TBP.POST_CREATE_AT, TBP.POST_TITLE, TBS.SOM_CATEGORY
    private Long id;
    private Date postCreateAt;
    private String postTitle;
    private String somCategory;

    public MyPagePostLikeDTO(PostVO postVO, SomVO somVO) {
        this.id = postVO.getId();
        this.postCreateAt = postVO.getPostCreateAt();
        this.postTitle = postVO.getPostTitle();
        this.somCategory = somVO.getSomCategory();
    }

}
