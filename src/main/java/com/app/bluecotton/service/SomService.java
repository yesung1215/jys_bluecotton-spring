package com.app.bluecotton.service;

import com.app.bluecotton.domain.dto.SomJoinResponseDTO;
import com.app.bluecotton.domain.dto.SomReadResponseDTO;
import com.app.bluecotton.domain.dto.SomResponseDTO;
import com.app.bluecotton.domain.vo.som.SomJoinVO;
import com.app.bluecotton.domain.vo.som.SomLikeVO;
import com.app.bluecotton.domain.vo.som.SomVO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SomService {
    //  솜 등록
    public void registerSom(SomVO somVO);

    //  솜 상세 조회
    public SomResponseDTO findById(Long somId, String memberEmail);

    public Integer selectSomMaxPage(Map<String, Object> map);

    public List<SomResponseDTO> findByCategoryAndType(Map<String, Object> map);

    //  솜 전체 조회
    public List<SomResponseDTO> findAllSom();

    //  솜 전체 주소 조회
    public List<String> findAllAddress();

    //  솜 좋아요
    public void addLike(Long somId);

    //  솜 삭제
    public void withdraw(Long somId);

    public void registerSomJoin(SomJoinVO somJoinVO);

    public List<SomJoinResponseDTO> selectAllSomJoinList(Long somId);

    public void deleteSomJoin(Long somJoinId);

    public void insertSomLike(SomLikeVO somLikeVO);

    public Integer selectSomLikeCount(Long somId);

    public Boolean selectIsSomLike(SomLikeVO somLikeVO);

    public void deleteSomLike(SomLikeVO somLikeVO);
}
