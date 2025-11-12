package com.app.bluecotton.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PortOneResponse {
    private String impUid;
    private String merchantUid;
    private Long taskId;
    private String buyer_name;
}
