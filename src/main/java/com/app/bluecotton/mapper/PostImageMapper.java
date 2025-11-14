package com.app.bluecotton.mapper;

import com.app.bluecotton.domain.dto.post.PostImageUpdateDTO;
import com.app.bluecotton.domain.vo.post.PostImageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostImageMapper {

    void insertImgTemp(PostImageVO vo);

    void updateImgPostId(PostImageUpdateDTO dto);

    List<PostImageVO> selectImagesByPostId(Long postId);

    void updatePostId(@Param("imageId") Long imageId,
                      @Param("postId") Long postId);

    void updateThumbnail(@Param("imageId") Long imageId,
                         @Param("postId") Long postId);

    void insertDefaultImage(@Param("postId") Long postId);

}
