package com.app.bluecotton.domain.vo.som;

import lombok.Data;

@Data
public class SomReviewVO {
    private Long id;
    private boolean somReviewIsChecked;
    private String somReviewContent;
    private Long userId;
    private Long somId;
}
