package com.app.bluecotton.repository;

import com.app.bluecotton.domain.dto.post.*;
import com.app.bluecotton.domain.vo.post.*;
import com.app.bluecotton.mapper.MemberMapper;
import com.app.bluecotton.mapper.PostMapper;
import com.app.bluecotton.service.PostImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class PostDAO {
    private final PostMapper postMapper;
    private final PostImageService postImageService;

    // 게시글 목록 조회 (좋아요 여부 포함)
    public List<PostMainDTO> findPosts(
            String somCategory,
            String orderType,
            Long memberId,
            String q,
            int page,
            int size
    ) {
        return postMapper.select(somCategory, orderType, memberId, q, page, size);
    }

    // Total 게시글 수 처리
    public int countPosts(String somCategory, String q) {
        return postMapper.countPosts(somCategory, q);
    }

    // 게시글 등록
    public void insert(PostVO postVO) {
        postMapper.insert(postVO);
    }

    // 오늘 해당 솜에 이미 게시글 있는지 검사
    public int existsTodayPostInSom(Long memberId, Long somId) {
        return postMapper.existsTodayPostInSom(memberId, somId);
    }


    // 게시글 삭제 관련
    public void deletePostById(Long postId) { postMapper.deletePostById(postId); }
    public void deleteLikesByPostId(Long postId) { postMapper.deleteLikesByPostId(postId); }
    public void deletePostImages(Long postId) { postMapper.deletePostImages(postId); }
    public void deleteReportsByPostId(Long postId) { postMapper.deleteReportsByPostId(postId); }
    public void deleteRecentsByPostId(Long postId) { postMapper.deleteRecentsByPostId(postId); }
    public void deleteCommentsByPostId(Long postId) { postMapper.deleteCommentsByPostId(postId); }
    public void deleteCommentLikesByPostId(Long postId) { postMapper.deleteCommentLikesByPostId(postId); }
    public void deleteCommentReportsByPostId(Long postId) { postMapper.deleteCommentReportsByPostId(postId); }
    public void deleteRepliesByPostId(Long postId) { postMapper.deleteRepliesByPostId(postId); }
    public void deleteReplyLikesByPostId(Long postId) { postMapper.deleteReplyLikesByPostId(postId); }
    public void deleteReplyReportsByPostId(Long postId) { postMapper.deleteReplyReportsByPostId(postId); }

    // 댓글 삭제 관련
    public void deleteCommentById(Long commentId) { postMapper.deleteCommentById(commentId); }
    public void deleteCommentLikesByCommentId(Long commentId) { postMapper.deleteCommentLikesByCommentId(commentId); }
    public void deleteCommentReportsByCommentId(Long commentId) { postMapper.deleteCommentReportsByCommentId(commentId); }
    public void deleteRepliesByCommentId(Long commentId) { postMapper.deleteRepliesByCommentId(commentId); }
    public void deleteReplyLikesByCommentId(Long commentId) { postMapper.deleteReplyLikesByCommentId(commentId); }
    public void deleteReplyReportsByCommentId(Long commentId) { postMapper.deleteReplyReportsByCommentId(commentId); }


    // 답글 삭제 관련
    public void deleteReplyById(Long replyId) { postMapper.deleteReplyById(replyId); }
    public void deleteReplyLikeByReplyId(Long replyId) { postMapper.deleteReplyLikeByReplyId(replyId); }
    public void deleteReplyReportByReplyId(Long replyId) { postMapper.deleteReplyReportByReplyId(replyId); }

    //  임시 저장
    public void insertDraft(PostDraftVO postDraftVO) {
        postMapper.insertDraft(postDraftVO);
    }

    //  임시저장 불러오기
    public PostDraftVO findDraftById(Long id) {
        return postMapper.selectDraftById(id);
    }

    //  임시저장 삭제
    public void deleteDraftById(Long id) {
        postMapper.deleteDraftById(id);
    }

    // 참여하는 솜 카테고리 불러오기(글쓰기)
    public List<SomCategoryDTO> findJoinedCategories(Long memberId) {
        return postMapper.findJoinedSomsByMemberId(memberId);
    }

    // 수정 할 게시글 조회
    public PostModifyDTO findByIdForUpdate(Long postId) {
        PostModifyDTO postModifyDTO = postMapper.findByIdForUpdate(postId);

        // 이미지 불러오기
        List<PostImageVO> images = postImageService.selectImagesByPostId(postId);

        List<Long> ids = images.stream()
                .map(PostImageVO::getId)
                .toList();

        postModifyDTO.setPostImageIds(ids);

        return postModifyDTO;
    }

    // 게시글 수정
    public void update(PostVO postVO) { postMapper.update(postVO);}


    // 게시글 좋아요 여부 확인
    public boolean existsLike(Long postId, Long memberId) {
        return postMapper.existsLike(postId, memberId) > 0;
    }

    // 게시글 좋아요
    public void insertLike(Long postId, Long memberId) {
        postMapper.insertLike(postId, memberId);
    }

    // 게시글 좋아요 삭제
    public void deleteLike(Long postId, Long memberId) {
        postMapper.deleteLike(postId, memberId);
    }

    // 댓글 좋아요 여부 확인
    public boolean existsCommentLike(Long commentId, Long memberId) {
        return postMapper.existsCommentLike(commentId, memberId) > 0;
    }

    // 댓글 좋아요
    public void insertCommentLike(Long commentId, Long memberId) {
        postMapper.insertCommentLike(commentId, memberId);
    }

    // 댓글 좋아요 삭제
    public void deleteCommentLike(Long commentId, Long memberId) {
        postMapper.deleteCommentLike(commentId, memberId);
    }

    // 대댓글 좋아요 여부 확인
    public boolean existsReplyLike(Long replyId, Long memberId) {
        return postMapper.existsReplyLike(replyId, memberId) > 0;
    }

    // 대댓글 좋아요
    public void insertReplyLike(Long replyId, Long memberId) {
        postMapper.insertReplyLike(replyId, memberId);
    }

    // 대댓글 좋아요 삭제
    public void deleteReplyLike(Long replyId, Long memberId) {
        postMapper.deleteReplyLike(replyId, memberId);
    }

    //  조회수 + 1(게시물 상세 조회 시)
    public void updateReadCount(Long postId) {
        postMapper.updateReadCount(postId);
    }

    //  최근 본 글 게시물(게시물 상세 조회 시)
    public void registerRecent(Long memberId, Long postId) {
        postMapper.insertOrUpdateRecentView(memberId, postId);
    }

    //  댓글 추가
    public void insertComment(PostCommentVO postCommentVO) {
        postMapper.insertComment(postCommentVO);
    }

    //  답글 추가
    public void insertReply(PostReplyVO postReplyVO) {
        postMapper.insertReply(postReplyVO);
    }

    // 게시글 상세 조회
    public PostDetailDTO selectPost(Long postId, Long memberId) {
        return postMapper.selectPost(postId, memberId);
    }

    // 댓글 조회
    public List<PostCommentDTO> selectComment(Long postId, Long memberId) {
        return postMapper.selectComment(postId, memberId);
    }

    // 대댓글 조회
    public List<PostReplyDTO> selectReply(Long postCommentId, Long memberId) {
        return postMapper.selectReply(postCommentId, memberId);
    }

    // 다음글, 이전글
    public PostNeighborDTO selectNextPost(Long postId) {
        return postMapper.selectNextPost(postId);
    }

    public PostNeighborDTO selectPrevPost(Long postId) {
        return postMapper.selectPrevPost(postId);
    }

    // 게시글 신고
    public void reportPost(PostReportVO postReportVO) {
        postMapper.insertPostReport(postReportVO);
    }

    // 댓글 신고
    public void reportComment(PostCommentReportVO PostCommentReportVO) {
        postMapper.insertPostCommentReport(PostCommentReportVO);
    }

    // 답글 신고
    public void reportReply(PostReplyReportVO postReplyReportVO) {
        postMapper.insertPostReplyReport(postReplyReportVO);
    }

    // 게시글 신고 중복 여부
    public boolean existsPostReport(Long postId, Long memberId) {
        return postMapper.checkPostReportExists(postId, memberId) > 0;
    }

    // 댓글 신고 중복 여부
    public boolean existsCommentReport(Long postCommentId, Long memberId) {
        return postMapper.checkCommentReportExists(postCommentId, memberId) > 0;
    }

    // 답글 신고 중복 여부
    public boolean existsReplyReport(Long postReplyId, Long memberId) {
        return postMapper.checkReplyReportExists(postReplyId, memberId) > 0;
    }
}
