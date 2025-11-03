package com.app.bluecotton.domain.dto;

import com.app.bluecotton.domain.vo.member.MemberVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Member;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDTO {
    private Long id;
    private String memberName;
    private String memberNickName;
    private String memberEmail;
    private String memberAddress;
    private String memberGender;
    private Date memberBirth;
    private Long memberCandy;
    private String memberRank;
    private String memberProvider;

    public MemberResponseDTO(MemberVO memberVO) {
        this.memberName = memberVO.getMemberName();
        this.memberNickName = memberVO.getMemberNickName();
        this.memberEmail = memberVO.getMemberEmail();
        this.memberAddress = memberVO.getMemberAddress();
        this.memberGender = memberVO.getMemberGender();
        this.memberBirth = memberVO.getMemberBirth();
        this.memberCandy = memberVO.getMemberCandy();
        this.memberRank = memberVO.getMemberRank();
        this.memberProvider = memberVO.getMemberProvider();
    }
}
