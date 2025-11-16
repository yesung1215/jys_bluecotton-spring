package com.app.bluecotton.service;

import com.app.bluecotton.domain.vo.post.PostImageVO;

import java.util.List;

public interface PostImageService {

    /** 1) 임시 이미지 업로드 (POST_ID 없이 저장) */
    void createPostImageTemp(PostImageVO vo);

    /** 2) 단일 이미지 postId 연결 */
    void updatePostId(Long imageId, Long postId);

    /** 3) 해당 게시글의 모든 이미지 삭제 */
    void deleteImagesByPostId(Long postId);

    /** 4) 게시글 이미지 전체 조회 */
    List<PostImageVO> selectImagesByPostId(Long postId);

    /** 5) 기본 이미지 삽입 */
    void insertDefaultImage(Long postId);

    /** 6) postId 포함 이미지 INSERT (수정 시 재등록) */
    void insertImageWithPostId(PostImageVO vo);

}
