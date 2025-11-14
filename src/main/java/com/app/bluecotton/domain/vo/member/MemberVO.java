package com.app.bluecotton.domain.vo.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
//ID NUMBER CONSTRAINT PK_MEMBER PRIMARY KEY,
//MEMBER_NAME VARCHAR2(255) NOT NULL,
//MEMBER_NICKNAME VARCHAR2(255) NOT NULL,
//MEMBER_EMAIL VARCHAR2(255) UNIQUE NOT NULL,
//MEMBER_PASSWORD VARCHAR2(255),
//MEMBER_ADDRESS VARCHAR2(255),
//MEMBER_GENDER CHAR(10),
//MEMBER_BIRTH DATE NOT NULL,
//MEMBER_CANDY NUMBER DEFAULT 0 NOT NULL,
//MEMBER_RANK VARCHAR2(255) DEFAULT 'rookie' NOT NULL,
//MEMBER_PROVIDER VARCHAR2(255),
//MEMBER_PHONE VARCHAR2(255)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberVO {
    private Long id;
    private String memberName;
    private String memberNickname;
    private String memberEmail;
    private String memberPhone;
    private String memberPassword;
    private String memberAddress;
    private String memberDetailAddress;
    private String memberPostcode;
    private String memberGender;
    private Date memberBirth;
    private Integer memberCandy;
    private String memberRank;
    private String memberProvider;

    {
        this.memberNickname = "임시닉네임";
        this.memberProvider = "local";
        this.memberRank = "rookie";
        this.memberCandy = 0;
    }

    public MemberVO(MemberInsertSocialVO memberInsertSocialVO) {
        this.id = memberInsertSocialVO.getId();
        this.memberEmail = memberInsertSocialVO.getMemberEmail();
        this.memberName = memberInsertSocialVO.getMemberName();
        this.memberNickname = memberInsertSocialVO.getMemberNickname();
        this.memberProvider = memberInsertSocialVO.getMemberProvider(); }
};
