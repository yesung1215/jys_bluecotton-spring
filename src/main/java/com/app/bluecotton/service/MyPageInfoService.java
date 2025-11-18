package com.app.bluecotton.service;

import com.app.bluecotton.domain.dto.*;

import java.util.List;

public interface MyPageInfoService {

    //    회원정보 조회
    public MyPageInfoDTO selectMemberInfo(Long id);
    //    회원정보 수정
    public void updateInfo(MyPageInfoDTO myPageInfoDTO);
    //    회원 탈퇴 시 관련 데이터 전체 삭제
    public void deleteMemberCascade(Long id);

}
