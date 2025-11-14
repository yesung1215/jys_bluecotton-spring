package com.app.bluecotton.domain.dto;

import com.app.bluecotton.domain.vo.member.MemberProfileVO;
import com.app.bluecotton.domain.vo.member.MemberVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyPageInfoDTO {
    private Long id;
    private String memberName;
    private String memberNickname;
    private String memberEmail;
    private String memberPassword;
    private String memberAddress;
    private String memberDetailAddress;
    private String memberPostcode;
    private String memberGender;
    private Date memberBirth;
    private Integer memberCandy;
    private String memberRank;
    private String memberProvider;
    private String memberPhone;

    private Long memberPictureId;
    private String memberPicturePath;
    private String memberPictureName;

    public MyPageInfoDTO(MemberVO memberVO, MemberProfileVO memberProfileVO) {
        this.memberName = memberVO.getMemberName();
        this.memberNickname = memberVO.getMemberNickname();
        this.memberEmail = memberVO.getMemberEmail();
        this.memberPassword = memberVO.getMemberPassword();
        this.memberAddress = memberVO.getMemberAddress();
        this.memberDetailAddress = memberVO.getMemberDetailAddress();
        this.memberPostcode = memberVO.getMemberPostcode();
        this.memberGender = memberVO.getMemberGender();
        this.memberBirth = memberVO.getMemberBirth();
        this.memberCandy = memberVO.getMemberCandy();
        this.memberRank = memberVO.getMemberRank();
        this.memberProvider = memberVO.getMemberProvider();
        this.memberPhone = memberVO.getMemberPhone();

        this.memberPictureId = memberProfileVO.getId();
        this.memberPicturePath = memberProfileVO.getMemberProfilePath();
        this.memberPictureName = memberProfileVO.getMemberProfileName();

    }
}
