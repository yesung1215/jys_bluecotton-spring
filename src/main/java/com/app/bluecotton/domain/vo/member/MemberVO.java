package com.app.bluecotton.domain.vo.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    private String memberPhone;
};
