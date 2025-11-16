package com.app.bluecotton.service;

import com.app.bluecotton.domain.dto.post.*;
import com.app.bluecotton.domain.vo.post.*;
import com.app.bluecotton.domain.vo.som.SomImageVO;
import com.app.bluecotton.exception.PostException;
import com.app.bluecotton.repository.PostDAO;
import com.app.bluecotton.repository.PostImageDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {

    private final PostDAO postDAO;
    private final PostImageService postImageService;

    // 게시글 목록 조회
    @Override
    public List<PostMainDTO> getPosts(
            String somCategory,
            String orderType,
            Long memberId,
            String q,
            int page,
            int size
    ) {
        return postDAO.findPosts(somCategory, orderType, memberId, q, page, size);
    }

    // 토탈 게시글 수 처리
    @Override
    public int countPosts(String somCategory, String q) {
        return postDAO.countPosts(somCategory, q);
    }

    // 게시글 등록 + draft 자동 삭제 (트랜잭션)
    @Override
    public Long write(PostVO postVO, List<Long> postImageIds, Long draftId) {

        int count = postDAO.existsTodayPostInSom(postVO.getMemberId(), postVO.getSomId());
        if (count > 0) {
            throw new PostException("이미 오늘 해당 솜에 게시글을 작성했습니다.");
        }

        // 1) 게시글 등록
        postDAO.insert(postVO);

        // 2) 이미지 처리
        if (postImageIds != null && !postImageIds.isEmpty()) {
            boolean isFirst = true;

            for (Long imageId : postImageIds) {

                // 이미지 → postId 연결
                postImageService.updatePostId(imageId, postVO.getId());

            }

        } else {
            // 기본 썸네일 등록
            postImageService.insertDefaultImage(postVO.getId());
        }

        // 3) 임시저장 삭제
        if (draftId != null) {
            postDAO.deleteDraftById(draftId);
        }

        return postVO.getId();
    }

    // 회원이 참여 중인 솜 카테고리 목록 조회
    @Override
    public List<SomCategoryDTO> getJoinedCategories(Long memberId) {
        return postDAO.findJoinedCategories(memberId);
    }

    // 게시글 삭제
    @Override
    public void withdraw(Long postId) {
        // postId로 답글 관련 삭제
        postDAO.deleteReplyReportsByPostId(postId);
        postDAO.deleteReplyLikesByPostId(postId);
        postDAO.deleteRepliesByPostId(postId);
        // postId로 댓글 관련 삭제
        postDAO.deleteCommentReportsByPostId(postId);
        postDAO.deleteCommentLikesByPostId(postId);
        postDAO.deleteCommentsByPostId(postId);
        // postId로 게시글 관련 삭제
        postDAO.deleteLikesByPostId(postId);
        postDAO.deleteReportsByPostId(postId);
        postDAO.deletePostImages(postId);
        postDAO.deleteRecentsByPostId(postId);
        // 게시글 삭제
        postDAO.deletePostById(postId);
    }

    // 댓글 삭제
    @Override
    public void deleteComment(Long commentId) {
        // commentId로 답글 관련 삭제
        postDAO.deleteReplyLikesByCommentId(commentId);
        postDAO.deleteReplyReportsByCommentId(commentId);
        postDAO.deleteRepliesByCommentId(commentId);
        // commentId로 댓글 관련 삭제
        postDAO.deleteCommentReportsByCommentId(commentId);
        postDAO.deleteCommentLikesByCommentId(commentId);
        // commentId로 댓글 삭제
        postDAO.deleteCommentById(commentId);
    }

    // 답글 삭제
    @Override
    public void deleteReplyById(Long replyId) {
        // ReplyId로 답글 관련 삭제
        postDAO.deleteReplyLikeByReplyId(replyId);
        postDAO.deleteReplyReportByReplyId(replyId);
        // ReplyId로 답글 삭제
        postDAO.deleteReplyById(replyId);
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
    @Transactional
    public void modifyPost(PostModifyDTO dto) {

        Long postId = dto.getId();

        // 1) 게시글 기본 정보 수정
        postDAO.update(dto.toPostVO());

        // 2) 기존 이미지 전부 삭제 (이게 핵심)
        postImageService.deleteImagesByPostId(postId);

        // 3) 본문 내용에서 이미지 URL 다시 추출
        String content = dto.getPostContent();
        if (content == null) content = "";

        List<String> imageUrls = extractImageUrlsFromMarkdown(content);

        // 4) 에디터에 이미지 하나도 없으면 → 기본 썸네일 1장만 넣기
        if (imageUrls.isEmpty()) {
            postImageService.insertDefaultImage(postId);
            return;
        }

        // 5) 에디터에 남아있는 이미지들만 다시 INSERT
        for (String url : imageUrls) {
            PostImageVO vo = buildPostImageVOFromUrl(url, postId);
            if (vo != null) {
                postImageService.insertImageWithPostId(vo);
            }
        }
    }

    // Markdown에서 ![...](url) 형태의 url들만 추출
    private List<String> extractImageUrlsFromMarkdown(String md) {
        List<String> urls = new ArrayList<>();
        if (md == null) return urls;

        Pattern p = Pattern.compile("!\\[[^\\]]*\\]\\(([^)]+)\\)");
        Matcher m = p.matcher(md);
        while (m.find()) {
            urls.add(m.group(1));
        }
        return urls;
    }

    // URL → path / name 분리해서 VO 생성
    private PostImageVO buildPostImageVOFromUrl(String url, Long postId) {
        if (url == null || !url.contains("/")) return null;

        int idx = url.lastIndexOf("/");
        String path = url.substring(0, idx + 1);   // /upload/post/.../ 또는 https://.../
        String name = url.substring(idx + 1);      // 파일명만

        PostImageVO vo = new PostImageVO();
        vo.setPostImagePath(path);
        vo.setPostImageName(name);
        vo.setPostId(postId);
        return vo;
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

    // 댓글 등록
    @Override
    public void insertComment(PostCommentVO postCommentVO) {
        postDAO.insertComment(postCommentVO);
    }

    // 대댓글 등록
    @Override
    public void insertReply(PostReplyVO postReplyVO) {
        postDAO.insertReply(postReplyVO);
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

    // 게시글 상세 조회
    @Override
    public PostDetailDTO getPost(Long postId, Long memberId) {
        List<PostImageVO> postImages = postImageService.selectImagesByPostId(postId);

        // 조회수 증가
        postDAO.updateReadCount(postId);

        // 게시글(좋아요 여부 포함)
        PostDetailDTO detail = postDAO.selectPost(postId, memberId);

        if (detail == null) return null;

        // 댓글
        List<PostCommentDTO> comments = postDAO.selectComment(postId, memberId);

        // 각 댓글의 대댓글
        for (PostCommentDTO c : comments) {
            List<PostReplyDTO> replies = postDAO.selectReply(c.getId(), memberId);
            c.setReplies(replies);
        }

        detail.setPostImageList(postImages);
        detail.setComments(comments);

        return detail;
    }

    // 이전글 id
    @Override
    @Transactional(readOnly = true)
    public PostNeighborDTO getPrevPost(Long id) {
        return postDAO.selectPrevPost(id);
    }

    // 다음글 id
    @Override
    @Transactional(readOnly = true)
    public PostNeighborDTO getNextPost(Long id) {
        return postDAO.selectNextPost(id);
    }

    // 최근 본 글 추가
    @Override
    public void registerRecent(Long memberId, Long postId) {
        postDAO.registerRecent(memberId, postId);
    }

    // 게시글 신고
    @Override
    public void reportPost(PostReportVO postReportVO) {

        // 신고 중복 체크
        boolean exists = postDAO.existsPostReport(
                postReportVO.getPostId(),
                postReportVO.getMemberId()
        );
        if (exists) {
            throw new PostException("이미 이 게시글을 신고했습니다.");
        }
        // 신고 저장
        postDAO.reportPost(postReportVO);
    }

    // 댓글 신고
    @Override
    public void reportComment(PostCommentReportVO postCommentReportVO) {
        boolean exists = postDAO.existsCommentReport(
                postCommentReportVO.getPostCommentId(),
                postCommentReportVO.getMemberId()
        );
        if (exists) {
            throw new PostException("이미 이 댓글을 신고했습니다.");
        }
        postDAO.reportComment(postCommentReportVO);
    }

    // 답글 신고
    @Override
    public void reportReply(PostReplyReportVO postReplyReportVO) {
        boolean exists = postDAO.existsReplyReport(
                postReplyReportVO.getPostReplyId(),
                postReplyReportVO.getMemberId()
        );
        if (exists) {
            throw new PostException("이미 이 답글을 신고했습니다.");
        }
        postDAO.reportReply(postReplyReportVO);
    }
}
