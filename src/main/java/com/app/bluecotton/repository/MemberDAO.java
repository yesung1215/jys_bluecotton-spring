package com.app.bluecotton.repository;

import com.app.bluecotton.domain.vo.member.MemberInsertSocialVO;
import com.app.bluecotton.domain.vo.member.MemberProfileVO;
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

    //  회원 조회
    public Optional<MemberVO> findById(Long id){
        return memberMapper.selectById(id);
    }
    //  전체 조회
    public List<MemberVO> findAll(){
        return memberMapper.selectAll();
    }
    //  이메일로 아이디 찾기
    public Long findIdByMemberEmail(String memberEmail){
        return memberMapper.selectIdByEmail(memberEmail);
    }
    //  이메일 중복 체크
    public boolean existsByMemberEmail(String memberEmail){
        return memberMapper.existByMemberEmail(memberEmail);
    }
    public String findMemberEmailByNameAndPhone(String memberName, String memberPhone){
        return memberMapper.selectMemberEmailByNameAndPhone(memberName, memberPhone);
    }
    //  회원 가입
    public void save(MemberVO memberVO){
        memberMapper.insert(memberVO);
    }
    //  회원 가입(소셜)
    public void saveSocialMember(MemberInsertSocialVO memberInsertSocialVO){
        memberMapper.insertSocial(memberInsertSocialVO);
    }

    public void saveInsertDefaultProfileImage(MemberProfileVO memberProfileVO){
        memberMapper.insertDefaultProfileImage(memberProfileVO);
    }

    public Optional<MemberProfileVO> findMemberProfileImage(Long memberId){
        return memberMapper.selectMemberProfileImage(memberId);
    }
    //  회원 수정
    public void update(MemberVO memberVO){
        memberMapper.update(memberVO);
    }

    public void updatePassword(String memberEmail, String newPassword){
        memberMapper.updatePassword(memberEmail, newPassword);
    }

    //  회원 탈퇴
    public void delete(Long id){
        memberMapper.delete(id);
    }

    //  회원 주소 전체 조회
    public List<String> findAllMemberAddress(){
        return memberMapper.selectAllAddress();
    }

}
