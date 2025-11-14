package com.app.bluecotton.service;

import com.app.bluecotton.domain.dto.*;
import com.app.bluecotton.domain.vo.som.SomReviewVO;
import com.app.bluecotton.domain.vo.som.SomVO;

import java.util.List;

public interface MyPageSomService {

    //    마이페이지 회원 별 파티솜 솔로솜 조회
    public List<SomVO> selectById(Long id);
    //    마이페이지 솜 인증 추가
    public void insertSomCheckWithImages(MyPageSomCheckDTO myPageSomCheckDTO);
    //    마이페이지 솜 리뷰 추가
    public void insertSomReview(SomReviewVO somReviewVO);
    //    마이페이지 솜 인증 호출
    public List<MyPageSomCheckDTO> readSomCheck(Long id);
    //    마이페이지 솜 리뷰 호출
    public List<MyPageSomReviewDTO> readSomReview(Long id);
    //    마이페이지 랭크 호출
    public Long readRank(Long id);

}
