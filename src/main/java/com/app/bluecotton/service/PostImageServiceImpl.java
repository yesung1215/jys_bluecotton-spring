package com.app.bluecotton.service;

import com.app.bluecotton.domain.dto.post.PostImageUpdateDTO;
import com.app.bluecotton.domain.vo.post.PostImageVO;
import com.app.bluecotton.mapper.PostImageMapper;
import com.app.bluecotton.repository.PostImageDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor

public class PostImageServiceImpl implements PostImageService {

    private final PostImageDAO postImageDAO;

    @Override
    public void createPostImageTemp(PostImageVO vo) {
        postImageDAO.insertImgTemp(vo);
    }

    @Override
    public void updateInsertPostImage(PostImageUpdateDTO dto) {
        postImageDAO.updateImgPostId(dto);
    }

    @Override
    public List<PostImageVO> selectImagesByPostId(Long postId) {
        return postImageDAO.selectImagesByPostId(postId);
    }

    @Override
    public void updatePostId(Long imageId, Long postId) {
        postImageDAO.updatePostId(imageId, postId);
    }

//    @Override
//    public void updateThumbnail(Long imageId, Long postId) {
//        postImageDAO.updateThumbnail(imageId, postId);
//    }

    @Override
    public void insertDefaultImage(Long postId) {
        postImageDAO.insertDefaultImage(postId);
    }

}
