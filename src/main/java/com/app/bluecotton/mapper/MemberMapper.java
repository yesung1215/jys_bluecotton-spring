package com.app.bluecotton.mapper;

import com.app.bluecotton.domain.vo.member.MemberInsertSocialVO;
import com.app.bluecotton.domain.vo.member.MemberSocialVO;
import com.app.bluecotton.domain.vo.member.MemberVO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberMapper {
    //  일반 회원 가입
    public void insert(MemberVO memberVO);

    //  소셜 회원 가입
    public void insertSocial(MemberInsertSocialVO memberInsertSocialVO);

    //  회원 상세 조회
    public Optional<MemberVO> selectById(Long memberId);

    //  회원의 이메일로 아이디 조회
    public Long selectIdByEmail(String memberEmail);

    //  회원 전체 조회
    public List<MemberVO> selectAll();

    //  이메일 중복 확인
    public boolean existByMemberEmail(String memberEmail);

    //  회원 정보 수정
    public void update(MemberVO  memberVO);

    //  회원 탈퇴
    public void delete(Long memberId);

    //  전체 회원 주소 조회
    public List<String> selectAllAddress ();

}
