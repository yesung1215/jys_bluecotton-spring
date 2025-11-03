package com.app.bluecotton.service;

import com.app.bluecotton.domain.dto.MemberResponseDTO;
import com.app.bluecotton.domain.vo.member.MemberVO;
import com.app.bluecotton.exception.MemberException;
import com.app.bluecotton.repository.MemberDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class MemberServiceImpl implements MemberService {

    private final MemberDAO memberDAO;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(MemberVO memberVO) {
        if(memberDAO.existByMemberEmail(memberVO.getMemberEmail())){
            throw new MemberException("이미 존재하는 이메일입니다");
        }

        memberVO.setMemberPassword(passwordEncoder.encode(memberVO.getMemberPassword()));

        memberDAO.join(memberVO);
    }

    @Override
    public void showList(MemberVO memberVO) {
        memberDAO.showList();
    }

    @Override
    public boolean existByMemberEmail(String memberEmail) {
        return memberDAO.existByMemberEmail(memberEmail);
    }

    @Override
    public Long selectIdByEmail(String memberEmail) {

        return memberDAO.selectIdByEmail(memberEmail);
    }

    @Override
    public MemberResponseDTO getMemberById(Long memberId) {

        return memberDAO.selectMemberById(memberId).map(MemberResponseDTO::new).orElseThrow(()-> new MemberException("회원 조회 실패"));
    }

    @Override
    public List<String> findALlMemberAddress() {
        return memberDAO.findALlMemberAddress();
    }
}
