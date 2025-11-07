package com.app.bluecotton.service;

import com.app.bluecotton.domain.dto.MemberResponseDTO;
import com.app.bluecotton.domain.vo.member.MemberInsertSocialVO;
import com.app.bluecotton.domain.vo.member.MemberSocialVO;
import com.app.bluecotton.domain.vo.member.MemberVO;
import com.app.bluecotton.exception.MemberException;
import com.app.bluecotton.repository.MemberDAO;
import com.app.bluecotton.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class MemberServiceImpl implements MemberService {


    private final MemberDAO memberDAO;
    private final MemberSocialService memberSocialService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    // 이메일 중복 조회
    @Override
    public boolean existsByMemberEmail(String memberEmail) {
        return memberDAO.existsByMemberEmail(memberEmail);
    }

    // 회원가입
    @Override
    public Map<String, String> register(MemberVO memberVO) {

        // 1. 이메일 중복검사
        if(memberDAO.existsByMemberEmail(memberVO.getMemberEmail())) {
            throw new MemberException("이미 존재하는 회원입니다");
        }

        // 2. 비밀번호 암호화
        memberVO.setMemberPassword(passwordEncoder.encode(memberVO.getMemberPassword()));

        // 3. 회원 가입
        memberDAO.save(memberVO);
        return Map.of();
    }

    // 회원가입 소셜 (비밀번호가 없다)
    @Override
    public Map<String, String> registerSocial(
            MemberInsertSocialVO memberInsertSocialVO,
            MemberSocialVO memberSocialVO
    ) {

        Map<String, String> claim = new HashMap<>();
        Map<String, String> tokens = new HashMap<>();

        if(memberDAO.existsByMemberEmail(memberInsertSocialVO.getMemberEmail())) {
            throw new MemberException("이미 존재하는 회원입니다.");
        }

        // 회원 가입
        memberDAO.saveSocialMember(memberInsertSocialVO);

        // 가입한 회원 정보
        String memberEmail = memberInsertSocialVO.getMemberEmail();

        // 가입한 회원의 ID
        Long memberId = memberDAO.findIdByMemberEmail(memberEmail);

        String refreshToken = jwtTokenUtil.generateRefreshToken(claim);
        String accessToken = jwtTokenUtil.generateAccessToken(claim);

        // 소셜 테이블에 추가
        memberSocialVO.setMemberId(memberId);
        memberSocialService.registerMemberSocial(memberSocialVO);

        // 토큰을 담아서 반환
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        return tokens;
    }

    // 회원 이메일로 아이디 조회
    @Override
    public Long getMemberIdByMemberEmail(String memberEmail) {
        return memberDAO.findIdByMemberEmail(memberEmail);
    }

    // 회원 조회
    @Override
    public MemberResponseDTO getMemberById(Long id) {
        return memberDAO.findById(id).map(MemberResponseDTO::new).orElseThrow(() -> new MemberException("회원 조회 실패"));
    }

    @Override
    public List<String> findAllMemberAddress() {
        return memberDAO.findAllMemberAddress();
    }

    // 회원 정보 수정
    @Override
    public void modify(MemberVO memberVO) {
        memberDAO.update(memberVO);
    }

    // 회원 탈퇴
    @Override
    public void withdraw(Long id) {
        memberDAO.delete(id);
    }
}


