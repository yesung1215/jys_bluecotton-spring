package com.app.bluecotton.service;


import com.app.bluecotton.domain.dto.SomImageUpdateDTO;
import com.app.bluecotton.domain.dto.post.PostImageUpdateDTO;
import com.app.bluecotton.domain.vo.post.PostImageVO;
import com.app.bluecotton.domain.vo.som.SomImageVO;

import java.util.List;

public interface PostImageService {

    public void createPostImageTemp(PostImageVO postImageVO);

    public void updateInsertPostImage(PostImageUpdateDTO postImageUpdateDTO);

    public List<PostImageVO> selectImagesByPostId(Long postId);

}
