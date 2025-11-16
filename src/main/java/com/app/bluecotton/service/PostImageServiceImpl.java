package com.app.bluecotton.service;

import com.app.bluecotton.domain.vo.post.PostImageVO;
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

    /** 1) 임시 이미지 업로드 */
    @Override
    public void createPostImageTemp(PostImageVO vo) {
        postImageDAO.insertImgTemp(vo);
    }

    /** 2) 해당 게시글의 모든 이미지 삭제 */
    @Override
    public void deleteImagesByPostId(Long postId) {
        postImageDAO.deleteImagesByPostId(postId);
    }

    /** 3) 게시글 이미지 전체 조회 */
    @Override
    public List<PostImageVO> selectImagesByPostId(Long postId) {
        return postImageDAO.selectImagesByPostId(postId);
    }

    /** 4) 단일 이미지 postId 연결 */
    @Override
    public void updatePostId(Long imageId, Long postId) {
        postImageDAO.updatePostId(imageId, postId);
    }

    /** 5) 기본 이미지 삽입 */
    @Override
    public void insertDefaultImage(Long postId) {
        postImageDAO.insertDefaultImage(postId);
    }

    /** 6) postId 포함 이미지 INSERT */
    @Override
    public void insertImageWithPostId(PostImageVO vo) {
        postImageDAO.insertImageWithPostId(vo);
    }

}
