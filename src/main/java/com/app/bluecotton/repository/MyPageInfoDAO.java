package com.app.bluecotton.repository;

import com.app.bluecotton.domain.dto.MyPageInfoDTO;
import com.app.bluecotton.domain.dto.MyPagePostWriteDTO;
import com.app.bluecotton.domain.dto.MyPageSomCheckDTO;
import com.app.bluecotton.domain.dto.MyPageSomReviewDTO;
import com.app.bluecotton.domain.vo.som.SomReviewVO;
import com.app.bluecotton.mapper.MyPageInfoMapper;
import com.app.bluecotton.mapper.MyPageSomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MyPageInfoDAO {
    private final MyPageInfoMapper myPageInfoMapper;


    //    ① 회원정보 조회
    public MyPageInfoDTO selectMemberInfo(Long id) {
        return myPageInfoMapper.selectMemberInfo(id);
    }
    //    ① 회원정보 수정
    public void updateMemberInfo(MyPageInfoDTO myPageInfoDTO) {
        myPageInfoMapper.updateMemberInfo(myPageInfoDTO);
    }
    //    ② 회원 프로필 수정
    public void updateMemberProfile(MyPageInfoDTO myPageInfoDTO) {
        myPageInfoMapper.updateMemberProfile(myPageInfoDTO);
    }
    //    회원 탈퇴 시 관련 데이터 전체 삭제
    public void deleteMemberCascade(Long id) {
        myPageInfoMapper.deleteMemberCascade(id);
    }
}
