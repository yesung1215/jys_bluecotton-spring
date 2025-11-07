package com.app.bluecotton.repository;

import com.app.bluecotton.domain.vo.som.SomVO;
import com.app.bluecotton.mapper.MyTestMapper;
import com.app.bluecotton.mapper.SomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SomDAO {

    private final SomMapper somMapper;

    //  솜 등록
    public void save(SomVO somVO) {
        somMapper.insert(somVO);
    }

    //  솜 상세 조회
    public Optional<SomVO> findById(Long somId) {
        return somMapper.selectById(somId);
    }

    //  솜 카테고리별 조회
    public List<SomVO> findByCategory(String somCategory) {
        return somMapper.selectSomByCategory(somCategory);
    }

    //  솜 전체 조회
    public List<SomVO> findAllSom() {
        return somMapper.selectAll();
    }

    //  솜 전체 주소 조회
    public List<String> findAllSomAddress() {
        return somMapper.selectAllAddress();
    }

    //  솜 좋아요
    public void addLike(Long somId) {
        somMapper.updateLike(somId);
    }

    //  솜 삭제
    public void withdraw(Long somId) {
        somMapper.delete(somId);
    }
}
