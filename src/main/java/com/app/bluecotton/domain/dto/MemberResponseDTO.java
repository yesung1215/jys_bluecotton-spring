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
    private String memberEmail;
    private String memberPicturePath;
    private String memberPictureName;
    private String memberName;
    private String memberNickName;
    private String memberProvider;

    {
        this.setMemberPicturePath("/default");
        this.setMemberPictureName("member.jpg");
        this.setMemberNickName("임시닉네임");
        this.setMemberProvider("local");
    }

    public MemberResponseDTO(MemberVO memberVO) {
        this.id = memberVO.getId();
        this.memberEmail = memberVO.getMemberEmail();
        this.memberPicturePath = memberVO.getMemberPicturePath();
        this.memberPictureName = memberVO.getMemberPictureName();
        this.memberName = memberVO.getMemberName();
        this.memberNickName = memberVO.getMemberNickname();
        this.memberProvider = memberVO.getMemberProvider();
    }
}
