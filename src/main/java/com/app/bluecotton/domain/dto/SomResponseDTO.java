package com.app.bluecotton.domain.dto;

import com.app.bluecotton.domain.vo.member.MemberVO;
import com.app.bluecotton.domain.vo.som.SomVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SomResponseDTO {
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

    public SomResponseDTO(SomVO somVO) {
        this.id = somVO.getId();
        this.somTitle = somVO.getSomTitle();
        this.somCategory = somVO.getSomCategory();
        this.somAddress = somVO.getSomAddress();
        this.somType = somVO.getSomType();
        this.somStartDate = somVO.getSomStartDate();
        this.somEndDate = somVO.getSomEndDate();
        this.somLike = somVO.getSomLike();
        this.somContent = somVO.getSomContent();
        this.memberId = somVO.getMemberId();
    }
}
