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
public class MyPagePostWriteDTO {
//    TBM.ID, TBP.POST_TITLE, TBP.POST_CREATE_AT, TBS.SOM_CATEGORY
    private Long id;
    private Date postCreateAt;
    private String postTitle;
    private String somCategory;

    private MyPagePostWriteDTO(PostVO postVO, SomVO somVO) {
        this.id = postVO.getId();
        this.postCreateAt = postVO.getPostCreateAt();
        this.postTitle = postVO.getPostTitle();
        this.somCategory = somVO.getSomCategory();
    }
}
