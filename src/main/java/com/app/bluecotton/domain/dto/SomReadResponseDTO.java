package com.app.bluecotton.domain.dto;

import com.app.bluecotton.domain.vo.som.SomImageVO;
import com.app.bluecotton.domain.vo.som.SomVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SomReadResponseDTO {
    private Long id;
    private String somTitle;
    private String somCategory;
    private String somAddress;
    private String somType;
    private Date somStartDate;
    private Date somEndDate;
    private Integer somLike;
    private String somContent;
    private Integer somCount;
    private Long memberId;

    private List<SomImageVO> somImageList;
    private List<SomJoinResponseDTO> somJoinList;
    private MemberSomLeaderResponseDTO memberSomLeader;

    public SomReadResponseDTO(SomVO somVO) {
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
