package com.app.bluecotton.mapper;

import com.app.bluecotton.domain.dto.post.PostImageUpdateDTO;
import com.app.bluecotton.domain.vo.post.PostImageVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostImageMapper {
    public void insertImgTemp(PostImageVO postImageVO);

    public void updateImgPostId(PostImageUpdateDTO postImageUpdateDTO);

    public List<PostImageVO> selectImagesByPostId(Long postId);
}
