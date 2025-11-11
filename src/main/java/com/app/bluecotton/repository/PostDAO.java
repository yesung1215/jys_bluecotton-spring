package com.app.bluecotton.repository;

import com.app.bluecotton.domain.dto.post.*;
import com.app.bluecotton.domain.vo.post.PostCommentVO;
import com.app.bluecotton.domain.vo.post.PostDraftVO;
import com.app.bluecotton.domain.vo.post.PostReplyVO;
import com.app.bluecotton.domain.vo.post.PostVO;
import com.app.bluecotton.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostDAO {
    private final PostMapper postMapper;

    /* ===================== 🟦 게시글 ===================== */

    // 게시글 목록
    public List<PostMainDTO> findPosts(String somCategory, String orderType, Long memberId, String q) {
        return postMapper.select(somCategory, orderType, memberId, q);
    }

    // 게시글 등록
    public void insert(PostVO postVO) {
        postMapper.insert(postVO);
    }

    // 오늘 해당 솜에 이미 게시글 있는지 검사
    public boolean existsTodayPostInSom(Long memberId, Long somId) {
        return postMapper.existsTodayPostInSom(memberId, somId) > 0;
    }

    // 게시글 이미지 매핑
    public void updatePostIdByUrl(String url, Long postId) {
        postMapper.updatePostIdByUrl(url, postId);
    }

    public void insertDefaultImage(String postImagePath, String postImageName, Long postId) {
        postMapper.insertDefaultImage(postImagePath, postImageName, postId);
    }

    public void insertThumbnail(String url, Long postId) {
        postMapper.insertThumbnail(url, postId);
    }

    // 게시글 삭제 관련
    public void deletePostById(Long id) { postMapper.deletePostById(id); }
    public void deleteLikesByPostId(Long postId) { postMapper.deleteLikesByPostId(postId); }
    public void deletePostImages(Long postId) { postMapper.deletePostImages(postId); }
    public void deleteReportsByPostId(Long postId) { postMapper.deleteReportsByPostId(postId); }
    public void deleteRecentsByPostId(Long postId) { postMapper.deleteRecentsByPostId(postId); }

    /* ===================== 🟨 임시저장 ===================== */

    public void insertDraft(PostDraftVO postDraftVO) {
        postMapper.insertDraft(postDraftVO);
    }

    public PostDraftVO findDraftById(Long id) {
        return postMapper.selectDraftById(id);
    }

    public void deleteDraftById(Long id) {
        postMapper.deleteDraftById(id);
    }

    /* ===================== 🟩 카테고리 / 수정 ===================== */

    public List<SomCategoryDTO> findJoinedCategories(Long memberId) {
        return postMapper.findJoinedCategories(memberId);
    }

    public PostModifyDTO findByIdForUpdate(Long id) {
        return postMapper.findByIdForUpdate(id);
    }

    public void update(PostVO postVO) {
        postMapper.update(postVO);
    }

    /* ===================== 🟦 게시글 상세 ===================== */

    // 로그인 사용자용 (좋아요 여부 포함)
    public PostDetailDTO findPostDetailByIdWithLike(Long postId, Long memberId) {
        return postMapper.selectPostDetailByIdWithLike(postId, memberId);
    }

    public List<PostCommentDTO> findPostCommentsByPostIdWithLike(Long postId, Long memberId) {
        return postMapper.selectCommentsByPostIdWithLike(postId, memberId);
    }

    public List<PostReplyDTO> findPostRepliesByCommentIdWithLike(Long commentId, Long memberId) {
        return postMapper.selectRepliesByCommentIdWithLike(commentId, memberId);
    }

    // 비로그인 사용자용 (좋아요 제외)
    public PostDetailDTO findPostDetailWithoutLike(Long postId) {
        return postMapper.selectPostDetailWithoutLike(postId);
    }

    public List<PostCommentDTO> findPostCommentsByPostIdWithoutLike(Long postId) {
        return postMapper.selectCommentsByPostIdWithoutLike(postId);
    }

    public List<PostReplyDTO> findPostRepliesByCommentIdWithoutLike(Long commentId) {
        return postMapper.selectRepliesByCommentIdWithoutLike(commentId);
    }

    /* ===================== ❤️ 좋아요 ===================== */

    // 게시글 좋아요
    public boolean existsLike(Long postId, Long memberId) {
        return postMapper.existsLike(postId, memberId) > 0;
    }

    public void insertLike(Long postId, Long memberId) {
        postMapper.insertLike(postId, memberId);
    }

    public void deleteLike(Long postId, Long memberId) {
        postMapper.deleteLike(postId, memberId);
    }

    // 댓글 좋아요
    public boolean existsCommentLike(Long commentId, Long memberId) {
        return postMapper.existsCommentLike(commentId, memberId) > 0;
    }

    public void insertCommentLike(Long commentId, Long memberId) {
        postMapper.insertCommentLike(commentId, memberId);
    }

    public void deleteCommentLike(Long commentId, Long memberId) {
        postMapper.deleteCommentLike(commentId, memberId);
    }

    // 대댓글 좋아요
    public boolean existsReplyLike(Long replyId, Long memberId) {
        return postMapper.existsReplyLike(replyId, memberId) > 0;
    }

    public void insertReplyLike(Long replyId, Long memberId) {
        postMapper.insertReplyLike(replyId, memberId);
    }

    public void deleteReplyLike(Long replyId, Long memberId) {
        postMapper.deleteReplyLike(replyId, memberId);
    }

    /* ===================== 👁 조회수 / 최근 본 게시글 ===================== */

    public void updateReadCount(Long postId) {
        postMapper.updateReadCount(postId);
    }

    public void registerRecent(Long memberId, Long postId) {
        postMapper.insertOrUpdateRecentView(memberId, postId);
    }

    /* ===================== 💬 댓글 / 답글 ===================== */

    public void insertComment(PostCommentVO postCommentVO) {
        postMapper.insertComment(postCommentVO);
    }

    public void insertReply(PostReplyVO postReplyVO) {
        postMapper.insertReply(postReplyVO);
    }

    public void deleteComment(Long commentId) {
        postMapper.deleteComment(commentId);
    }

    public void deleteReply(Long replyId) {
        postMapper.deleteReply(replyId);
    }

    public PostDetailDTO selectTest(Long postId) { return postMapper.selectTest(postId); }
}
