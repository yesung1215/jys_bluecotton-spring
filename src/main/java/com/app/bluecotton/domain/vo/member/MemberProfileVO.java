package com.app.bluecotton.domain.vo.member;

import lombok.Data;

@Data
public class MemberProfileVO {
    private Long id;
    private String memberProfilePath;
    private String memberProfileName;
    private Long memberId;
}
