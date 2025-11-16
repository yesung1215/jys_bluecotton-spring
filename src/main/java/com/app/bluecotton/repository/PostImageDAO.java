package com.app.bluecotton.repository;

import com.app.bluecotton.domain.vo.post.PostImageVO;
import com.app.bluecotton.mapper.PostImageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostImageDAO {

    private final PostImageMapper postImageMapper;

    /** 1) 임시 저장 이미지 등록 (POST_ID 없이 업로드) */
    public void insertImgTemp(PostImageVO vo) {
        postImageMapper.insertImgTemp(vo);
    }

    /** 2) 단일 이미지 postId 연결 */
    public void updatePostId(Long imageId, Long postId) {
        postImageMapper.updatePostId(imageId, postId);
    }

    /** 3) postId로 전체 이미지 조회 */
    public List<PostImageVO> selectImagesByPostId(Long postId) {
        return postImageMapper.selectImagesByPostId(postId);
    }

    /** 4) 기본 이미지 삽입 */
    public void insertDefaultImage(Long postId) {
        postImageMapper.insertDefaultImage(postId);
    }

    /** 5) 해당 게시글의 모든 이미지 삭제 */
    public void deleteImagesByPostId(Long postId) {
        postImageMapper.deleteImagesByPostId(postId);
    }

    /** 6) postId 포함 이미지 INSERT */
    public void insertImageWithPostId(PostImageVO vo) {
        postImageMapper.insertImageWithPostId(vo);
    }

}
