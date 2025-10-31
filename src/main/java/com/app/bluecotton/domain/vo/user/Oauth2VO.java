package com.app.bluecotton.domain.vo.user;

import lombok.Data;

import java.util.Date;

@Data
public class Oauth2VO {
    private Long id;
    private String socialProvider;
    private Long socialProviderId;
    private Date socialConnectDate;
    private Long userId;
}
