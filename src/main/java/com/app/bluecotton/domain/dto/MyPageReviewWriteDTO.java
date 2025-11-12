package com.app.bluecotton.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MyPageReviewWriteDTO {

    private Long reviewId;
    private Long productId;
    private Long memberId;
    private Integer rating;
    private String content;
    private String imagePath;
    private String imageName;


}
