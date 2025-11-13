package com.app.bluecotton.mapper;

import com.app.bluecotton.domain.dto.SomJoinResponseDTO;
import com.app.bluecotton.domain.dto.SomResponseDTO;
import com.app.bluecotton.domain.vo.som.SomJoinVO;
import com.app.bluecotton.domain.vo.som.SomLikeVO;
import com.app.bluecotton.domain.vo.som.SomVO;
import org.apache.ibatis.annotations.Mapper;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface SomMapper {
    //  솜 등록
    public void insert(SomVO somVO);

    //  솜 상세 조회
    public Optional<SomVO> selectById(Long somId);

    public List<SomVO> selectSomListByCategoryAndType(Map<String, Object> map);

    public Integer selectSomMaxPage(Map<String, Object> map);

    //  솜 전체 조회
    public List<SomVO> selectAll();

    //  솜 전체 주소 조회
    public List<String> selectAllAddress();

    //  솜 좋아요
    public void updateLike(Long somId);

    //  솜 삭제
    public void delete(Long somId);

    public void insertSomJoin(SomJoinVO somJoinVO);

    public List<SomJoinResponseDTO> selectAllSomJoinList(Long somId);

    public void deleteSomJoin(Long somJoinId);

    public void insertSomLike(SomLikeVO somLikeVO);

    public Integer selectSomLikeCount(Long somId);

    public Boolean selectIsSomLike(SomLikeVO somLikeVO);

    public void deleteSomLike(SomLikeVO somLikeVO);
}
