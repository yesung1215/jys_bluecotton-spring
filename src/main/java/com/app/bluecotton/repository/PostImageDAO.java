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

    public void insertImgTemp(PostImageVO vo) {
        postImageMapper.insertImgTemp(vo);
    }

    public void updateImgPostId(PostImageUpdateDTO dto) {
        postImageMapper.updateImgPostId(dto);
    }

    public List<PostImageVO> selectImagesByPostId(Long postId) {
        return postImageMapper.selectImagesByPostId(postId);
    }

    public void updatePostId(Long imageId, Long postId) {
        postImageMapper.updatePostId(imageId, postId);
    }

    public void updateThumbnail(Long imageId, Long postId) {
        postImageMapper.updateThumbnail(imageId, postId);
    }

    public void insertDefaultImage(Long postId) {
        postImageMapper.insertDefaultImage(postId);
    }

}
