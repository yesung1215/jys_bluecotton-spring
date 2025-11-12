package com.app.bluecotton.domain.dto;

import com.app.bluecotton.domain.vo.member.MemberVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberSomLeaderResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String memberName;
    private String memberNickname;
    private String memberPicturePath;
    private String memberPictureName;
    private String memberProvider;

    {
        this.setMemberPicturePath("/default");
        this.setMemberPictureName("member.jpg");
        this.setMemberNickname("임시닉네임");
        this.setMemberProvider("local");
    }

    public MemberSomLeaderResponseDTO(MemberVO memberVO) {
        this.id = memberVO.getId();
        this.memberPicturePath = memberVO.getMemberPicturePath();
        this.memberPictureName = memberVO.getMemberPictureName();
        this.memberName = memberVO.getMemberName();
        this.memberNickname = memberVO.getMemberNickname();
        this.memberProvider = memberVO.getMemberProvider();
    }
}
