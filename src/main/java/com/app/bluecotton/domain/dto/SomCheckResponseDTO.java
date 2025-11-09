package com.app.bluecotton.domain.dto;

import com.app.bluecotton.domain.vo.som.SomCheckImageVO;
import com.app.bluecotton.domain.vo.som.SomCheckVO;
import com.app.bluecotton.domain.vo.som.SomVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SomCheckResponseDTO {
    private Long id;
    private boolean somCheckIsChecked;
    private String somCheckContent;
    private Long memberId;
    private Long somId;
    private Long somCheckImageId;
    private List<String> somCheckImagePath;
    private List<String> somCheckImageName;
    private List<Long> somCheckId;

    public SomCheckResponseDTO(SomCheckVO somCheckVO, SomCheckImageVO somCheckImageVO) {
        this.id = somCheckVO.getId();
        this.somCheckIsChecked = somCheckVO.isSomCheckIsChecked();
        this.somCheckContent = somCheckVO.getSomCheckContent();
        this.memberId = somCheckVO.getMemberId();
        this.somId = somCheckVO.getSomId();
        this.somCheckImageId = somCheckImageVO.getId();
        this.somCheckImagePath = somCheckImageVO.getSomCheckImagePath();
        this.somCheckImageName = somCheckImageVO.getSomCheckImageName();
        this.somCheckId = somCheckImageVO.getSomCheckId();
    }
}
