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
    public void createPostImageTemp(PostImageVO postImageVO) {
        postImageDAO.insertImgTemp(postImageVO);
    }

    @Override
    public void updateInsertPostImage(PostImageUpdateDTO postImageUpdateDTO) {
        postImageDAO.updateImgPostId(postImageUpdateDTO);
    }

    @Override
    public List<PostImageVO> selectImagesByPostId(Long postId) {
        return postImageDAO.selectImagesByPostId(postId);
    }

}
