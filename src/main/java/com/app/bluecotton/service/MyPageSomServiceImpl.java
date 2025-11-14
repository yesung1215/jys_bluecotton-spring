package com.app.bluecotton.service;

import com.app.bluecotton.domain.dto.*;
import com.app.bluecotton.domain.vo.som.SomReviewVO;
import com.app.bluecotton.domain.vo.som.SomVO;
import com.app.bluecotton.mapper.MyPageSomMapper;
import com.app.bluecotton.repository.MyPageSomDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageSomServiceImpl implements MyPageSomService {
    private final MyPageSomDAO myPageSomDAO;
    private final MyPageSomMapper myPageSomMapper;

    //    마이페이지 회원 별 파티솜 솔로솜 조회
    @Override
    public List<SomVO> selectById(Long id){
        return myPageSomDAO.selectById(id);
    }

    //    마이페이지 솜 인증 추가
    @Transactional
    public void insertSomCheckWithImages(MyPageSomCheckDTO dto) {
        // 1. SomCheck 저장 → somCheckId 세팅됨
        myPageSomMapper.insertSomCheck(dto);

        // 2. 이미지들 저장
        if (dto.getImages() != null) {
            for (MyPageSomCheckImageDTO image : dto.getImages()) {
                image.setSomCheckId(dto.getSomCheckId());
                myPageSomMapper.insertSomCheckImage(image);
            }
        }
    }

    //    마이페이지 솜 리뷰 추가
    @Override
    public void insertSomReview(SomReviewVO somReviewVO){
        myPageSomDAO.insertSomReview(somReviewVO);
    }

    //    마이페이지 솜 인증 호출
    @Override
    public List<MyPageSomCheckDTO> readSomCheck(Long id){
        return myPageSomDAO.readSomCheck(id);
    }

    //    마이페이지 솜 리뷰 호출
    @Override
    public List<MyPageSomReviewDTO> readSomReview(Long id){ return myPageSomDAO.readSomReview(id); }

    //    마이페이지 랭크 호출
    @Override
    public Long readRank(Long id){ return myPageSomDAO.readRank(id); }
}
