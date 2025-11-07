package com.app.bluecotton.service;

import com.app.bluecotton.domain.dto.SomResponseDTO;
import com.app.bluecotton.domain.vo.som.SomVO;
import com.app.bluecotton.exception.SomException;
import com.app.bluecotton.mapper.SomMapper;
import com.app.bluecotton.repository.SomDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class SomServiceImpl implements SomService {

    private final SomDAO somDAO;

    //  솜 등록
    @Override
    public void registerSom(SomVO somVO) {
        somDAO.save(somVO);
    }

    //  솜 상세 조회
    @Override
    public SomResponseDTO findById(Long somId) {
        return somDAO.findById(somId).map(SomResponseDTO::new).orElseThrow(() -> new SomException("솜을 불러오지 못했습니다"));
    }

    //  솜 카테고리별 조회
    @Override
    public List<SomVO> findByCategory(String somCategory) {
        return somDAO.findByCategory(somCategory);
    }

    //  솜 전체 조회
    @Override
    public List<SomVO> findAllSom() {
        return somDAO.findAllSom();
    }

    @Override
    public List<String> findAllAddress() {
        return somDAO.findAllSomAddress();
    }

    //  솜 좋아요
    @Override
    public void addLike(Long somId) {
        somDAO.addLike(somId);
    }

    //  솜 삭제
    @Override
    public void withdraw(Long somId) {
        somDAO.withdraw(somId);
    }
}
