package com.app.bluecotton.mapper;

import com.app.bluecotton.domain.dto.*;
import com.app.bluecotton.domain.vo.som.SomVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyPageInfoMapper {



    //    회원정보 조회
    public MyPageInfoDTO selectMemberInfo(Long id);
    //    ① 회원정보 수정
    public void updateMemberInfo(MyPageInfoDTO myPageInfoDTO);
    //    ② 회원 프로필 수정
    public void updateMemberProfile(MyPageInfoDTO myPageInfoDTO);
    //    회원 탈퇴 시 관련 데이터 전체 삭제
    public void deleteMemberCascade(Long id);

}
