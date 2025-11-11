package com.app.bluecotton.mapper;

import com.app.bluecotton.domain.dto.post.*;
import com.app.bluecotton.domain.vo.post.PostCommentVO;
import com.app.bluecotton.domain.vo.post.PostDraftVO;
import com.app.bluecotton.domain.vo.post.PostReplyVO;
import com.app.bluecotton.domain.vo.post.PostVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostMapper {

    // 게시물 목록 조회
    List<PostMainDTO> select(
            @Param("somCategory") String somCategory,
            @Param("orderType") String orderType,
            @Param("memberId") Long memberId,
            @Param("q") String q
    );

    // 게시물 등록
    void insert(PostVO postVO);

    // 오늘 해당 솜에 이미 등록했는지 검사
    int existsTodayPostInSom(@Param("memberId") Long memberId, @Param("somId") Long somId);

    // 이미지 URL에 PostID 매핑
    void updatePostIdByUrl(@Param("url") String url, @Param("postId") Long postId);

    // 기본 이미지 등록
    void insertDefaultImage(
            @Param("postImagePath") String postImagePath,
            @Param("postImageName") String postImageName,
            @Param("postId") Long postId
    );

    // 썸네일 이미지 등록
    void insertThumbnail(@Param("url") String url, @Param("postId") Long postId);

    // 게시글 삭제 관련
    void deletePostById(Long postId);
    void deleteLikesByPostId(Long postId);
    void deletePostImages(Long postId);
    void deleteReportsByPostId(Long postId);
    void deleteRecentsByPostId(Long postId);

    void insertDraft(PostDraftVO postDraftVO);
    PostDraftVO selectDraftById(Long id);
    void deleteDraftById(Long id);

    List<SomCategoryDTO> findJoinedCategories(Long memberId);

    PostModifyDTO findByIdForUpdate(@Param("id") Long id);
    void update(PostVO postVO);

    // 게시글 상세 (로그인)
    PostDetailDTO selectPostDetailByIdWithLike(@Param("postId") Long postId,
                                               @Param("memberId") Long memberId);

    // 게시글 상세 (비로그인)
    PostDetailDTO selectPostDetailWithoutLike(@Param("postId") Long postId);

    // 댓글 목록 (로그인)
    List<PostCommentDTO> selectCommentsByPostIdWithLike(@Param("postId") Long postId,
                                                        @Param("memberId") Long memberId);

    // 댓글 목록 (비로그인)
    List<PostCommentDTO> selectCommentsByPostIdWithoutLike(@Param("postId") Long postId);

    // 대댓글 목록 (로그인)
    List<PostReplyDTO> selectRepliesByCommentIdWithLike(@Param("commentId") Long commentId,
                                                        @Param("memberId") Long memberId);

    // 대댓글 목록 (비로그인)
    List<PostReplyDTO> selectRepliesByCommentIdWithoutLike(@Param("commentId") Long commentId);

    // 게시물 좋아요
    int existsLike(@Param("postId") Long postId, @Param("memberId") Long memberId);
    void insertLike(@Param("postId") Long postId, @Param("memberId") Long memberId);
    void deleteLike(@Param("postId") Long postId, @Param("memberId") Long memberId);

    // 댓글 좋아요
    int existsCommentLike(@Param("commentId") Long commentId, @Param("memberId") Long memberId);
    void insertCommentLike(@Param("commentId") Long commentId, @Param("memberId") Long memberId);
    void deleteCommentLike(@Param("commentId") Long commentId, @Param("memberId") Long memberId);

    // 답글 좋아요
    int existsReplyLike(@Param("replyId") Long replyId, @Param("memberId") Long memberId);
    void insertReplyLike(@Param("replyId") Long replyId, @Param("memberId") Long memberId);
    void deleteReplyLike(@Param("replyId") Long replyId, @Param("memberId") Long memberId);

    // 조회수 _ 최근 본 게시글
    void updateReadCount(Long postId);
    void insertOrUpdateRecentView(@Param("memberId") Long memberId, @Param("postId") Long postId);

    // 댓글 / 답글
    void insertComment(PostCommentVO postCommentVO);
    void insertReply(PostReplyVO postReplyVO);
    void deleteComment(Long commentId);
    void deleteReply(Long replyId);

    //  테스트
    public PostDetailDTO selectTest(Long id);
}
