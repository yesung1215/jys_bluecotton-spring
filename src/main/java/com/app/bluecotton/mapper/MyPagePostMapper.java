package com.app.bluecotton.mapper;

import com.app.bluecotton.domain.dto.MyPagePostLikeDTO;
import com.app.bluecotton.domain.dto.MyPagePostWriteDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MyPagePostMapper {

    //    마이페이지 내가 쓴 글
    public List<MyPagePostWriteDTO> readPostWrite(Long id);
    //    마이페이지 내가 좋아요한 글
    public List<MyPagePostLikeDTO> readPostLike(Long id);
    //    내가 작성한 글 삭제
    public void deletePostWrite(Long id);
    //    내가 좋아요한 글 삭제
    public void deletePostLike(Long id);
    //    내가 임시저장한 글 삭제
    public void deletePostSave(Long id);
    //    내가 최근에 본 글 삭제
    public void deletePostRecent(Long id);
}
