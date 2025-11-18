package com.app.bluecotton.service;

import com.app.bluecotton.domain.dto.*;
import com.app.bluecotton.domain.vo.member.MemberVO;
import com.app.bluecotton.exception.MemberException;
import com.app.bluecotton.repository.MemberDAO;
import com.app.bluecotton.repository.MyPageInfoDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyPageInfoServiceImpl implements MyPageInfoService {
    private final MyPageInfoDAO myPageInfoDAO;
    private final MemberDAO memberDAO;

    //    회원정보 조회
    @Override
    public MyPageInfoDTO selectMemberInfo(Long id) {
        return myPageInfoDAO.selectMemberInfo(id);
    }

    //    회원정보 수정
    @Override
    public void updateInfo(MyPageInfoDTO info) {
        Optional<MemberVO> origin = memberDAO.findById(info.getId());

        // 닉네임이 바뀐 경우에만 중복 체크
        if (!Objects.equals(origin.get().getMemberNickname(), info.getMemberNickname())
                && memberDAO.existsByMemberNickname(info.getMemberNickname())) {
            throw new MemberException("이미 존재하는 닉네임 입니다");
        }

        // 전화번호가 바뀐 경우에만 중복 체크
        if (!Objects.equals(origin.get().getMemberPhone(), info.getMemberPhone())
                && memberDAO.existsByMemberPhone(info.getMemberPhone())) {
            throw new MemberException("이미 존재하는 전화번호 입니다");
        }

        myPageInfoDAO.updateMemberInfo(info);
        myPageInfoDAO.updateMemberProfile(info);
    }
    //    회원 탈퇴 시 관련 데이터 전체 삭제
    @Override
    public void deleteMemberCascade(Long id) {
        myPageInfoDAO.deleteMemberCascade(id);
    }

}
