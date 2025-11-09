package com.app.bluecotton.domain.vo.som;

import lombok.Data;

import java.util.List;

@Data
public class SomCheckImageVO {
    private Long id;
    private List<String> somCheckImagePath;
    private List<String> somCheckImageName;
    private List<Long> somCheckId;
}
