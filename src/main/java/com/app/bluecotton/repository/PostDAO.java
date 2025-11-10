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

    //    게시글 목록 조회
    public List<PostMainDTO> findPosts(String somCategory, String orderType, Long memberId, String q) {
        return postMapper.select(somCategory, orderType, memberId, q);
    }

    //    게시물 등록
    public void insert(PostVO postVO) {
        postMapper.insert(postVO);
    }

    //    게시물 등록 검사
    public boolean existsTodayPostInSom(Long memberId, Long somId) {
        return postMapper.existsTodayPostInSom(memberId, somId) > 0;
    }

    //    게시물 이미지 삽입
    public void updatePostIdByUrl(String url, Long postId) {
        postMapper.updatePostIdByUrl(url, postId);
    }
    //      게시물 기본 이미지
    public void insertDefaultImage(String postImagePath, String postImageName, Long postId) {
        postMapper.insertDefaultImage(postImagePath, postImageName, postId);
    }

    public void insertThumbnail(String url, Long postId) {
        postMapper.insertThumbnail(url, postId);
    }

    //    게시물 삭제
    public void deletePostById(Long id){
        postMapper.deletePostById(id);
    }

    public void deleteLikesByPostId(Long postId) {
        postMapper.deleteLikesByPostId(postId);
    }

    public void deletePostImages(Long postId) {
        postMapper.deletePostImages(postId);
    }

    public void deleteReportsByPostId(Long postId) {
        postMapper.deleteReportsByPostId(postId);
    }

    public void deleteRecentsByPostId(Long postId) {
        postMapper.deleteRecentsByPostId(postId);
    }

    //    임시 저장 등록
    public void insertDraft(PostDraftVO  postDraftVO) {
        postMapper.insertDraft(postDraftVO);
    }

    //    임시저장 조회 (이어쓰기용)
    public PostDraftVO findDraftById(Long id) {
        return postMapper.selectDraftById(id);
    }

    //    임시저장 삭제 (마이페이지 or 작성완료 후 삭제용)
    public void deleteDraftById(Long id) {
        postMapper.deleteDraftById(id);
    }

    //     드롭다운 조회
    public List<SomCategoryDTO> findJoinedCategories(Long memberId) {
        return postMapper.findJoinedCategories(memberId);
    }

    // 수정용 게시글 조회
    public PostModifyDTO findByIdForUpdate(Long id) {
        return postMapper.findByIdForUpdate(id);
    }

    // 게시글 수정
    public void update(PostVO postVO) {
        postMapper.update(postVO);
    }

    // 게시글 상세 조회
    public PostDetailDTO findPostDetailByIdWithLike(Long postId, Long memberId) {
        return postMapper.selectPostDetailByIdWithLike(postId, memberId);
    }

    // 게시글 댓글 조회
    public List<PostCommentDTO> findPostCommentsByPostIdWithLike(Long postId, Long memberId) {
        return postMapper.selectCommentsByPostIdWithLike(postId, memberId);
    }

    // 게시글 답글 조회
    public List<PostReplyDTO> findPostRepliesByCommentIdWithLike(Long commentId, Long memberId) {
        return postMapper.selectRepliesByCommentIdWithLike(commentId, memberId);
    }

    // 댓글 좋아요 여부
    public boolean existsCommentLike(Long commentId, Long memberId) {
        return postMapper.existsCommentLike(commentId, memberId) > 0;
    }

    // 답글 좋아요 여부
    public boolean existsReplyLike(Long replyId, Long memberId) {
        return postMapper.existsReplyLike(replyId, memberId) > 0;
    }

    // 댓글 좋아요 추가
    public void insertCommentLike(Long commentId, Long memberId) {
        postMapper.insertCommentLike(commentId, memberId);
    }

    // 댓글 좋아요 삭제
    public void deleteCommentLike(Long commentId, Long memberId) {
        postMapper.deleteCommentLike(commentId, memberId);
    }

    // 답글 좋아요 추가
    public void insertReplyLike(Long replyId, Long memberId) {
        postMapper.insertReplyLike(replyId, memberId);
    }

    // 답글 좋아요 삭제
    public void deleteReplyLike(Long replyId, Long memberId) {
        postMapper.deleteReplyLike(replyId, memberId);
    }

    // 조회수 + 1(상세 조회 시)
    public void updateReadCount(Long postId) {
        postMapper.updateReadCount(postId);
    }

    // 최근 본 게시물 추가(상세 조회 시)
    public void registerRecent(Long memberId ,Long postId) {
        postMapper.insertOrUpdateRecentView(memberId, postId);
    }

    // 댓글 추가
    public void insertComment(PostCommentVO postCommentVO) {
        postMapper.insertComment(postCommentVO);
    }

    // 답글 추가
    public void insertReply(PostReplyVO postReplyVO) {
        postMapper.insertReply(postReplyVO);
    }

    // 댓글 삭제
    public void deleteComment(Long commentId) {
        postMapper.deleteComment(commentId);
    }

    // 답글 삭제
    public void deleteReply(Long ReplyId) {
        postMapper.deleteReply(ReplyId);
    }

    // 좋아요 여부
    public boolean existsLike(Long postId, Long memberId) {
        return postMapper.existsLike(postId, memberId) > 0;
    }

    // 좋아요 등록
    public void insertLike(Long postId, Long memberId) {
        postMapper.insertLike(postId, memberId);
    }

    // 좋아요 삭제
    public void deleteLike(Long postId, Long memberId) {
        postMapper.deleteLike(postId, memberId);
    }




}
