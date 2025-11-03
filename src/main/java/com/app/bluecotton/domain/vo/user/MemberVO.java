package com.app.bluecotton.domain.vo.user;

import lombok.Data;

import java.util.Date;

@Data
public class MemberVO {
    private Long id;
    private String memberName;
    private String memberNickname;
    private String memberEmail;
    private String memberPassword;
    private String memberAddress;
    private String memberGender;
    private Date memberBirth;
    private Long memberCandy;
    private String memberRank;
    private String memberProvider;
}
