package com.app.bluecotton.domain.vo.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberInsertSocialVO {
    private Long id;
    private String memberName;
    private String memberNickname;
    private String memberEmail;
    private String memberProvider;
    {
        this.memberNickname = "임시닉네임";
    }
}
