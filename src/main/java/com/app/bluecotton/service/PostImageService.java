package com.app.bluecotton.service;


import com.app.bluecotton.domain.dto.SomImageUpdateDTO;
import com.app.bluecotton.domain.dto.post.PostImageUpdateDTO;
import com.app.bluecotton.domain.vo.post.PostImageVO;
import com.app.bluecotton.domain.vo.som.SomImageVO;

import java.util.List;

public interface PostImageService {

    void createPostImageTemp(PostImageVO postImageVO);

    void updateInsertPostImage(PostImageUpdateDTO dto);

    List<PostImageVO> selectImagesByPostId(Long postId);

    void updatePostId(Long imageId, Long postId);

//    void updateThumbnail(Long imageId, Long postId);

    void insertDefaultImage(Long postId);

}
