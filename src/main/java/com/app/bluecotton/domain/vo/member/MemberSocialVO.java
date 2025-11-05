package com.app.bluecotton.domain.vo.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberSocialVO {
    private Long id;
    private String memberSocialProvider;
    private String memberSocialProviderId;
    private Long memberId;
}
