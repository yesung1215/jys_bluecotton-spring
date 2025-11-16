package com.app.bluecotton.mapper;

import com.app.bluecotton.domain.dto.post.*;
import com.app.bluecotton.domain.vo.post.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface PostMapper {

    // 게시물 목록 조회 (좋아요 여부 포함)
    List<PostMainDTO> select(
            @Param("somCategory") String somCategory,
            @Param("orderType") String orderType,
            @Param("memberId") Long memberId,
            @Param("q") String q,
            @Param("page") int page,
            @Param("size") int size
    );

    // Total 게시글 수
    int countPosts(
            @Param("somCategory") String somCategory,
            @Param("q") String q
    );

    // 게시물 등록
    public void insert(PostVO postVO);

    // 오늘 해당 솜에 이미 등록했는지 검사
    public int existsTodayPostInSom(@Param("memberId") Long memberId, @Param("somId") Long somId);

    // 이미지 URL에 PostID 매핑
    public void updatePostIdByUrl(@Param("url") String url, @Param("postId") Long postId);

    // 기본 이미지 등록
    public void insertDefaultImage(
            @Param("postImagePath") String postImagePath,
            @Param("postImageName") String postImageName,
            @Param("postId") Long postId
    );

    // 썸네일 이미지 등록
    public void insertThumbnail(@Param("url") String url, @Param("postId") Long postId);

    // 게시글 삭제 관련
    public void deletePostById(Long postId);
    public void deleteLikesByPostId(Long postId);
    public void deletePostImages(Long postId);
    public void deleteReportsByPostId(Long postId);
    public void deleteRecentsByPostId(Long postId);
    public void deleteCommentsByPostId(Long postId);
    public void deleteCommentLikesByPostId(Long postId);
    public void deleteCommentReportsByPostId(Long postId);
    public void deleteRepliesByPostId(Long postId);
    public void deleteReplyLikesByPostId(Long postId);
    public void deleteReplyReportsByPostId(Long postId);


    // 댓글 삭제 관련
    public void deleteCommentById(Long commentId);
    public void deleteCommentLikesByCommentId(Long commentId);
    public void deleteCommentReportsByCommentId(Long commentId);
    public void deleteRepliesByCommentId(Long commentId);
    public void deleteReplyLikesByCommentId(Long commentId);
    public void deleteReplyReportsByCommentId(Long commentId);

    // 답글 삭제 관련
    public void deleteReplyById(Long replyId);
    public void deleteReplyLikeByReplyId(Long replyId);
    public void deleteReplyReportByReplyId(Long replyId);

    // 임시 저장 관련
    public void insertDraft(PostDraftVO postDraftVO);
    public PostDraftVO selectDraftById(Long id);
    public void deleteDraftById(Long id);

    // 참여하고 있는 솜 카테고리 조회
    public List<SomCategoryDTO> findJoinedSomsByMemberId(Long memberId);

    // 수정할 게시글 조회
    public PostModifyDTO findByIdForUpdate(@Param("id") Long id);
    // 게시글 수정
    public void update(PostVO postVO);

    // 게시물 좋아요
    public int existsLike(@Param("postId") Long postId, @Param("memberId") Long memberId);
    public void insertLike(@Param("postId") Long postId, @Param("memberId") Long memberId);
    public void deleteLike(@Param("postId") Long postId, @Param("memberId") Long memberId);

    // 댓글 좋아요
    public int existsCommentLike(@Param("commentId") Long commentId, @Param("memberId") Long memberId);
    public void insertCommentLike(@Param("commentId") Long commentId, @Param("memberId") Long memberId);
    public void deleteCommentLike(@Param("commentId") Long commentId, @Param("memberId") Long memberId);

    // 답글 좋아요
    public int existsReplyLike(@Param("replyId") Long replyId, @Param("memberId") Long memberId);
    public void insertReplyLike(@Param("replyId") Long replyId, @Param("memberId") Long memberId);
    public void deleteReplyLike(@Param("replyId") Long replyId, @Param("memberId") Long memberId);

    // 조회수 _ 최근 본 게시글
    public void updateReadCount(Long postId);
    public void insertOrUpdateRecentView(@Param("memberId") Long memberId, @Param("postId") Long postId);

    // 댓글 / 답글
    public void insertComment(PostCommentVO postCommentVO);
    public void insertReply(PostReplyVO postReplyVO);

    // 게시글 상세조회
    PostDetailDTO selectPost(
            @Param("postId") Long postId,
            @Param("memberId") Long memberId
    );

    // 게시글 댓글조회
    List<PostCommentDTO> selectComment(
            @Param("postId") Long postId,
            @Param("memberId") Long memberId
    );

    // 게시글 답글조회
    List<PostReplyDTO> selectReply(
            @Param("commentId") Long commentId,
            @Param("memberId") Long memberId
    );

    // 다음글, 이전글
    public PostNeighborDTO selectNextPost(Long id);
    public PostNeighborDTO selectPrevPost(Long id);

    // 게시글 신고
    public void insertPostReport(PostReportVO postReportVO);
    // 댓글 신고
    public void insertPostCommentReport(PostCommentReportVO postCommentReportVO);
    // 답글 신고
    public void insertPostReplyReport(PostReplyReportVO postReplyReportVO);

    // 게시글 신고 중복 확인
    public int checkPostReportExists(Long postId, Long memberId);
    // 댓글 신고 중복 확인
    public int checkCommentReportExists(Long postCommentId, Long memberId);
    // 답글 신고 중복 확인
    public int checkReplyReportExists(Long postReplyId, Long memberId);

}
