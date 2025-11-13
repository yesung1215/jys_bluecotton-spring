package com.app.bluecotton.domain.vo.som;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SomLikeVO {
    private Long id;
    private Long somId;
    private Long memberId;
}
