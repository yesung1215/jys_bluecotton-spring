package com.app.bluecotton.domain.vo.som;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class SomCheckVO {
    private Long id;
    private boolean somCheckIsChecked;
    private String somCheckContent;
    private Long memberId;
    private Long somId;
}
