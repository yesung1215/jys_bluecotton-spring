package com.app.bluecotton.repository;

import com.app.bluecotton.domain.vo.member.MemberVO;
import com.app.bluecotton.mapper.MemberMapper;
import com.app.bluecotton.mapper.MyTestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberDAO {
    private final MemberMapper memberMapper;

    //  회원 가입
    public void join(MemberVO memberVO) {
        memberMapper.insert(memberVO);
    }

    //  회원 전체 조회
    public List<MemberVO> showList(){
        return memberMapper.selectAll();
    }

    //  이메일 중복체크
    public boolean existByMemberEmail(String memberEmail){
        return memberMapper.existByMemberEmail(memberEmail);
    }

    //  이메일로 아이디 찾기
    public Long selectIdByEmail(String memberEmail){
        return memberMapper.selectIdByEmail(memberEmail);
    }

    //  회원 조회
    public Optional<MemberVO> selectMemberById(Long memberId){
        return memberMapper.select(memberId);
    }

    //  회원 주소 전체 조회
    public List<String> findALlMemberAddress(){
        return memberMapper.selectAllAddress();
    }
}
