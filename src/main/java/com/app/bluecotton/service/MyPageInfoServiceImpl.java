package com.app.bluecotton.service;

import com.app.bluecotton.domain.dto.*;
import com.app.bluecotton.repository.MyPageInfoDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MyPageInfoServiceImpl implements MyPageInfoService {
    private final MyPageInfoDAO myPageInfoDAO;

    //    회원정보 조회
    @Override
    public MyPageInfoDTO selectMemberInfo(Long id) {
        return myPageInfoDAO.selectMemberInfo(id);
    }

    //    회원정보 수정
    @Override
    public void updateInfo(MyPageInfoDTO info) {
        myPageInfoDAO.updateMemberInfo(info);
        myPageInfoDAO.updateMemberProfile(info);
    }


}
