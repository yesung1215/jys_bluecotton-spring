package com.app.bluecotton.repository;

import com.app.bluecotton.domain.dto.SomImageUpdateDTO;
import com.app.bluecotton.domain.dto.post.PostImageUpdateDTO;
import com.app.bluecotton.domain.vo.post.PostImageVO;
import com.app.bluecotton.domain.vo.som.SomImageVO;
import com.app.bluecotton.mapper.PostImageMapper;
import com.app.bluecotton.mapper.SomImageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostImageDAO {

    private final PostImageMapper postImageMapper;

    public void insertImgTemp(PostImageVO postImageVO){
        postImageMapper.insertImgTemp(postImageVO);
    }

    public void updateImgPostId(PostImageUpdateDTO postImageUpdateDTO){
        postImageMapper.updateImgPostId(postImageUpdateDTO);
    }

    public List<PostImageVO> selectImagesByPostId(Long postId){
        return postImageMapper.selectImagesByPostId(postId);
    }

}
