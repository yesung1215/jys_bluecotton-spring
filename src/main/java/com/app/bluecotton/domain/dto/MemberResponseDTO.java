package com.app.bluecotton.domain.dto;

import com.app.bluecotton.domain.vo.member.MemberVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.lang.reflect.Member;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String memberName;
    private String memberNickname;
    private String memberEmail;
    private String memberPhone;
    private String memberAddress;
    private String memberDetailAddress;
    private String memberPostcode;
    private String memberPicturePath;
    private String memberPictureName;
    private String memberGender;
    private Date memberBirth;
    private Integer memberCandy;
    private String memberRank;
    private String memberProvider;

    {
        this.setMemberPicturePath("/default");
        this.setMemberPictureName("member.jpg");
        this.setMemberNickname("임시닉네임");
        this.setMemberProvider("local");
    }

    public MemberResponseDTO(MemberVO memberVO) {
        this.id = memberVO.getId();
        this.memberEmail = memberVO.getMemberEmail();
        this.memberName = memberVO.getMemberName();
        this.memberNickname = memberVO.getMemberNickname();
        this.memberProvider = memberVO.getMemberProvider();
        this.memberGender = memberVO.getMemberGender();
        this.memberBirth = memberVO.getMemberBirth();
        this.memberCandy = memberVO.getMemberCandy();
        this.memberRank = memberVO.getMemberRank();
        this.memberPhone = memberVO.getMemberPhone();
        this.memberAddress = memberVO.getMemberAddress();
        this.memberPhone = memberVO.getMemberPhone();
        this.memberDetailAddress = memberVO.getMemberDetailAddress();
        this.memberPostcode = memberVO.getMemberPostcode();
    }
}
