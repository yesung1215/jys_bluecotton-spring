package com.app.bluecotton.repository;

import com.app.bluecotton.domain.dto.MyPagePostLikeDTO;
import com.app.bluecotton.domain.dto.MyPagePostWriteDTO;
import com.app.bluecotton.mapper.MyPagePostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MyPagePostDAO {
    private final MyPagePostMapper myPagePostMapper;

    //    마이페이지 내가 쓴 글
    public List<MyPagePostWriteDTO> readPostWrite(Long id) {
        return myPagePostMapper.readPostWrite(id);
    }
    //    마이페이지 내가 좋아요한 글
    public List<MyPagePostLikeDTO> readPostLike(Long id) { return myPagePostMapper.readPostLike(id); }
    //    내가 작성한 글 삭제
    public void deletePostWrite(Long id){ myPagePostMapper.deletePostWrite(id); };
    //    내가 좋아요한 글 삭제
    public void deletePostLike(Long id){ myPagePostMapper.deletePostLike(id); };
    //    내가 임시저장한 글 삭제
    public void deletePostSave(Long id){ myPagePostMapper.deletePostSave(id); };
    //    내가 최근에 본 글 삭제
    public void deletePostRecent(Long id){ myPagePostMapper.deletePostRecent(id); };
}
