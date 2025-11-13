package com.app.bluecotton.repository;

import com.app.bluecotton.domain.dto.SomJoinResponseDTO;
import com.app.bluecotton.domain.dto.SomResponseDTO;
import com.app.bluecotton.domain.vo.som.SomJoinVO;
import com.app.bluecotton.domain.vo.som.SomLikeVO;
import com.app.bluecotton.domain.vo.som.SomVO;
import com.app.bluecotton.mapper.MyTestMapper;
import com.app.bluecotton.mapper.SomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SomDAO {

    private final SomMapper somMapper;

    //  솜 등록
    public void save(SomVO somVO) {
        somMapper.insert(somVO);
    }

    public Integer selectSomMaxPage(Map<String, Object> map) {
        return somMapper.selectSomMaxPage(map);
    }

    //  솜 상세 조회
    public Optional<SomVO> findById(Long somId) {
        return somMapper.selectById(somId);
    }

    public List<SomVO> findSomListByCategoryAndType(Map<String, Object> map) {
        return somMapper.selectSomListByCategoryAndType(map);
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

    public void insertSomJoin(SomJoinVO somJoinVO) {
        somMapper.insertSomJoin(somJoinVO);
    }

    public List<SomJoinResponseDTO> readSomJoinList(Long id) {
        return somMapper.selectAllSomJoinList(id);
    }

    public void deleteSomJoin(Long somJoinId) {
        somMapper.deleteSomJoin(somJoinId);
    }

    public void insertSomLike(SomLikeVO somLikeVO) {
        somMapper.insertSomLike(somLikeVO);
    }

    public Integer selectSomLikeCount(Long somId) {
        return somMapper.selectSomLikeCount(somId);
    }

    public Boolean selectIsSomLike(SomLikeVO somLikeVO) {
        return somMapper.selectIsSomLike(somLikeVO);
    }

    public void deleteSomLike(SomLikeVO somLikeVO) {
        somMapper.deleteSomLike(somLikeVO);
    }
}
