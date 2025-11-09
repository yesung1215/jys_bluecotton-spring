package com.app.bluecotton.service;

import com.app.bluecotton.domain.dto.SomResponseDTO;
import com.app.bluecotton.domain.vo.som.SomVO;

import java.util.List;
import java.util.Optional;

public interface SomService {
    //  솜 등록
    public void registerSom(SomVO somVO);

    //  솜 상세 조회
    public SomResponseDTO findById(Long somId);

    //  솜 카테고리별 조회
    public List<SomVO> findByCategory(String somCategory);

    //  솜 타입별 조회
    public List<SomVO> findByType(String somType);

    //  솜 전체 조회
    public List<SomVO> findAllSom();

    //  솜 전체 주소 조회
    public List<String> findAllAddress();

    //  솜 좋아요
    public void addLike(Long somId);

    //  솜 삭제
    public void withdraw(Long somId);
}
