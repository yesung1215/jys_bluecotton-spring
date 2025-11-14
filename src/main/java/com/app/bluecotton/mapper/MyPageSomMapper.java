package com.app.bluecotton.mapper;

import com.app.bluecotton.domain.dto.*;
import com.app.bluecotton.domain.vo.som.SomReviewVO;
import com.app.bluecotton.domain.vo.som.SomVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyPageSomMapper {

    //    솜 상세 조회
    public List<SomVO> selectById(Long id);
    // 마이페이지 솜 인증 추가 (부모)
    void insertSomCheck(MyPageSomCheckDTO myPageSomCheckDTO);
    // 마이페이지 솜 인증 이미지 추가 (자식)
    void insertSomCheckImage(MyPageSomCheckImageDTO myPageSomCheckImageDTO);
    //    마이페이지 솜 리뷰 추가
    public void insertSomReview(SomReviewVO somReviewVO);
    //    마이페이지 솜 인증 호출
    public List<MyPageSomCheckDTO> readSomCheck(Long id);
    //    마이페이지 솜 리뷰 호출
    public List<MyPageSomReviewDTO> readSomReview(Long id);
    //    마이페이지 랭크 호출
    public Long readRank(Long id);

}
