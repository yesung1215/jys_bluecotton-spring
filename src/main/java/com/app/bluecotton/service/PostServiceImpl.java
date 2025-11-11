package com.app.bluecotton.service;

import com.app.bluecotton.domain.dto.post.*;
import com.app.bluecotton.domain.vo.post.PostCommentVO;
import com.app.bluecotton.domain.vo.post.PostDraftVO;
import com.app.bluecotton.domain.vo.post.PostReplyVO;
import com.app.bluecotton.domain.vo.post.PostVO;
import com.app.bluecotton.repository.PostDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {

    private final PostDAO postDAO;

    // 게시글 목록 조회
    @Override
    public List<PostMainDTO> getPosts(String somCategory, String orderType, Long memberId, String q) {
        return postDAO.findPosts(somCategory, orderType, memberId, q);
    }

    // 게시글 등록 + draft 자동 삭제 (트랜잭션)
    @Override
    public void write(PostVO postVO, List<String> imageUrls) {
        write(postVO, imageUrls, null);
    }

    // 오버로딩: draftId까지 받는 버전 (등록 + 임시저장 자동삭제)
    @Transactional(rollbackFor = Exception.class)
    public void write(PostVO postVO, List<String> imageUrls, Long draftId) {

        // 게시글 등록
        postDAO.insert(postVO);

        // 이미지 처리
        if (imageUrls != null && !imageUrls.isEmpty()) {
            boolean isFirst = true;
            for (String url : imageUrls) {
                postDAO.updatePostIdByUrl(url, postVO.getId());
                if (isFirst) {
                    postDAO.insertThumbnail(url, postVO.getId());
                    isFirst = false;
                }
            }
        } else {
            // 기본 썸네일 이미지 자동 등록
            postDAO.insertDefaultImage("/upload/default/", "default_post.jpg", postVO.getId());
        }

        // draftId 존재 시 자동 삭제 (트랜잭션 내부)
        if (draftId != null) {
            postDAO.deleteDraftById(draftId);
        }

    }

    // 회원이 참여 중인 솜 카테고리 목록 조회
    @Override
    public List<SomCategoryDTO> getJoinedCategories(Long memberId) {
        return postDAO.findJoinedCategories(memberId);
    }

    // 게시글 삭제
    @Override
    public void withdraw(Long postId) {
        postDAO.deleteLikesByPostId(postId);
        postDAO.deleteReportsByPostId(postId);
        postDAO.deletePostImages(postId);
        postDAO.deleteRecentsByPostId(postId);
        postDAO.deletePostById(postId);
    }

    // 임시저장 등록 / 조회 / 삭제
    @Override
    public void registerDraft(PostDraftVO postDraftVO) {
        postDAO.insertDraft(postDraftVO);
    }

    @Override
    public PostDraftVO getDraft(Long id) {
        return postDAO.findDraftById(id);
    }

    @Override
    public void deleteDraft(Long id) {
        postDAO.deleteDraftById(id);
    }

    // 수정 게시글 조회 / 수정
    @Override
    public PostModifyDTO getPostForUpdate(Long id) {
        return postDAO.findByIdForUpdate(id);
    }

    @Override
    public void modifyPost(PostVO postVO) {
        postDAO.update(postVO);
    }

    // 댓글 좋아요 토글
    @Override
    public void toggleCommentLike(Long commentId, Long memberId) {
        if (postDAO.existsCommentLike(commentId, memberId)) {
            postDAO.deleteCommentLike(commentId, memberId);
        } else {
            postDAO.insertCommentLike(commentId, memberId);
        }
    }

    // 대댓글 좋아요 토글
    @Override
    public void toggleReplyLike(Long replyId, Long memberId) {
        if (postDAO.existsReplyLike(replyId, memberId)) {
            postDAO.deleteReplyLike(replyId, memberId);
        } else {
            postDAO.insertReplyLike(replyId, memberId);
        }
    }

    // 댓글 등록 / 삭제
    @Override
    public void insertComment(PostCommentVO postCommentVO) {
        postDAO.insertComment(postCommentVO);
    }

    @Override
    public void deleteComment(Long commentId) {
        postDAO.deleteComment(commentId);
    }

    // 대댓글 등록 / 삭제
    @Override
    public void insertReply(PostReplyVO postReplyVO) {
        postDAO.insertReply(postReplyVO);
    }

    @Override
    public void deleteReply(Long replyId) {
        postDAO.deleteReply(replyId);
    }

    // 게시글 좋아요 토글
    @Override
    public void toggleLike(Long postId, Long memberId) {
        if (postDAO.existsLike(postId, memberId)) {
            postDAO.deleteLike(postId, memberId);
        } else {
            postDAO.insertLike(postId, memberId);
        }
    }

    @Override
    public PostDetailDTO selectTest(Long postId) {
//        조회수 추가
        postDAO.updateReadCount(postId);
//        최근 본 게시물 테이블 추가
//        postDAO.registerRecent(memberId, postId);

        PostDetailDTO post;
        List<PostCommentDTO> comments;

        post = postDAO.findPostDetailWithoutLike(postId);
        comments = postDAO.findPostCommentsByPostIdWithoutLike(postId);

        // 댓글 + 대댓글 계층 세팅
        for (PostCommentDTO comment : comments) {
            List<PostReplyDTO> replies = postDAO.findPostRepliesByCommentIdWithoutLike(comment.getCommentId());
            comment.setReplies(replies);
        }

        post.setComments(comments);
        return post;
    }

    // 게시글 상세 조회 (로그인 / 비로그인 자동 분기)
    @Override
    public PostDetailDTO getPostDetail(Long postId, Long memberId) {
        // 조회수 증가
        postDAO.updateReadCount(postId);

        PostDetailDTO post;
        List<PostCommentDTO> comments;

        if (memberId != null) {
            // 로그인 사용자 → 좋아요 여부 포함 + 최근  본 글 등록
            postDAO.registerRecent(memberId, postId);
            post = postDAO.findPostDetailByIdWithLike(postId, memberId);
            comments = postDAO.findPostCommentsByPostIdWithLike(postId, memberId);
        } else {
            // 비로그인 사용자 → 좋아요 여부 제외
            post = postDAO.findPostDetailWithoutLike(postId);
            comments = postDAO.findPostCommentsByPostIdWithoutLike(postId);
        }

        // 댓글 + 대댓글 계층 세팅
        for (PostCommentDTO comment : comments) {
            List<PostReplyDTO> replies = (memberId != null)
                    ? postDAO.findPostRepliesByCommentIdWithLike(comment.getCommentId(), memberId)
                    : postDAO.findPostRepliesByCommentIdWithoutLike(comment.getCommentId());
            comment.setReplies(replies);
        }

        post.setComments(comments);
        return post;
    }
}
