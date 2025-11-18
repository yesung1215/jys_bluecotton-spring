package com.app.bluecotton.domain.dto;

import com.app.bluecotton.domain.vo.som.SomImageVO;
import com.app.bluecotton.domain.vo.som.SomJoinVO;
import com.app.bluecotton.domain.vo.som.SomReviewVO;
import com.app.bluecotton.domain.vo.som.SomVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyPageSomAllDTO {
    // ID, SOM_TITLE, SOM_CATEGORY, SOM_ADDRESS, SOM_TYPE, SOM_START_DATE, SOM_END_DATE, SOM_LIKE, SOM_CONTENT, TBSJ.MEMBER_ID
    private Long id;
    private String somTitle;
    private String somCategory;
    private String somAddress;
    private String somType;
    private Date somStartDate;
    private Date somEndDate;
    private Integer somLike;
    private String somContent;
    private Long memberId;

    private List<SomImageVO> somImageList;

    public MyPageSomAllDTO(SomVO somVO, SomJoinVO somJoinVO) {
        this.id = somVO.getId();
        this.somTitle = somVO.getSomTitle();
        this.somCategory = somVO.getSomCategory();
        this.somAddress = somVO.getSomAddress();
        this.somType = somVO.getSomType();
        this.somStartDate = somVO.getSomStartDate();
        this.somEndDate = somVO.getSomEndDate();
        this.somLike = somVO.getSomLike();
        this.somContent = somVO.getSomContent();
        this.memberId = somJoinVO.getMemberId();
    }
}
