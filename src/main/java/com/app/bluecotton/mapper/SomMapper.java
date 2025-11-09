package com.app.bluecotton.mapper;

import com.app.bluecotton.domain.vo.som.SomVO;
import org.apache.ibatis.annotations.Mapper;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Mapper
public interface SomMapper {
    //  솜 등록
    public void insert(SomVO somVO);

    //  솜 상세 조회
    public Optional<SomVO> selectById(Long somId);

    //  솜 카테고리별 조회
    public List<SomVO> selectSomByCategory(String somCategory);

    //  솜 타입별 조회
    public List<SomVO> selectSomByType(String somType);

    //  솜 전체 조회
    public List<SomVO> selectAll();

    //  솜 전체 주소 조회
    public List<String> selectAllAddress();

    //  솜 좋아요
    public void updateLike(Long somId);

    //  솜 삭제
    public void delete(Long somId);
}
