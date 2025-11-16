package com.app.bluecotton.mapper;

import com.app.bluecotton.domain.vo.post.PostImageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostImageMapper {

    /** 1) 임시 이미지 저장 (POST_ID 없이 먼저 저장) */
    void insertImgTemp(PostImageVO vo);

    /** 2) 단일 이미지 postId 연결 */
    void updatePostId(@Param("imageId") Long imageId,
                      @Param("postId") Long postId);

    /** 3) POST_ID 기준 전체 이미지 삭제 */
    void deleteImagesByPostId(@Param("postId") Long postId);

    /** 4) 기본 이미지 삽입 */
    void insertDefaultImage(@Param("postId") Long postId);

    /** 5) 게시글 전체 이미지 조회 */
    List<PostImageVO> selectImagesByPostId(Long postId);

    /** 6) postId 포함 이미지 INSERT */
    void insertImageWithPostId(PostImageVO vo);
}
