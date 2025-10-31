package com.app.bluecotton.domain.vo.som;

import lombok.Data;

@Data
public class SomCheckVO {
    private Long id;
    private boolean somCheckIsChecked;
    private String somCheckContent;
    private Long userId;
    private Long somId;
}
