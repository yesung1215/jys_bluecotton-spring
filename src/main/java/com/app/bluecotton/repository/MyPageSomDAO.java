package com.app.bluecotton.repository;

import com.app.bluecotton.domain.dto.*;
import com.app.bluecotton.domain.vo.som.SomReviewVO;
import com.app.bluecotton.domain.vo.som.SomVO;
import com.app.bluecotton.mapper.MyPageSomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MyPageSomDAO {
    private final MyPageSomMapper  myPageSomMapper;

    //    마이페이지 회원 별 파티솜 솔로솜 조회
    public List<SomVO> selectById(Long id) {
        return myPageSomMapper.selectById(id);
    }
    //    마이페이지 솜 리뷰 추가
    public void insertSomReview(SomReviewVO somReviewVO) {
        myPageSomMapper.insertSomReview(somReviewVO);
    }
    //    마이페이지 솜 인증 호출
    public List<MyPageSomCheckDTO> readSomCheck(Long id) {
        return myPageSomMapper.readSomCheck(id);
    }
    //    마이페이지 솜 리뷰 호출
    public List<MyPageSomReviewDTO> readSomReview(Long id) {
        return myPageSomMapper.readSomReview(id);
    }
    //    마이페이지 랭크 호출
    public Long readRank(Long id) {
        return myPageSomMapper.readRank(id);
    }

}
