package com.app.bluecotton.service;

import com.app.bluecotton.domain.dto.MemberResponseDTO;
import com.app.bluecotton.domain.vo.member.MemberVO;

import java.util.List;

public interface MemberService {
    public void register(MemberVO memberVO);

    public void showList(MemberVO memberVO);

    public boolean existByMemberEmail(String memberEmail);

    public Long selectIdByEmail(String memberEmail);

    public MemberResponseDTO getMemberById(Long memberId);

    public List<String> findALlMemberAddress();
}
