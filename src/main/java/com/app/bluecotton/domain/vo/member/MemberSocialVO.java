package com.app.bluecotton.domain.vo.member;

import lombok.Data;

import java.util.Date;

@Data
public class MemberSocialVO {
    private Long id;
    private String memberSocialProvider;
    private Long memberSocialProviderId;
    private Date memberSocialConnectCreateAt;
    private Long memberId;
}
