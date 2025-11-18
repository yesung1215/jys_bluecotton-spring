package com.app.bluecotton.api.privateapi;

import com.app.bluecotton.domain.dto.*;
import com.app.bluecotton.service.MyPagePostService;
import com.app.bluecotton.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/private/my-page/*")
public class MyPagePostApi {

    private final MyPagePostService myPagePostService;
    private final PostService postService;

    //    마이페이지 내가 쓴 글
    @GetMapping("read-post-write")
    public ResponseEntity<ApiResponseDTO> readPostWrite(@RequestParam Long id){
        log.info("내가 쓴 글을 불러옵니다");
        log.info("출력: {}", myPagePostService.readPostWrite(id));
        List<MyPagePostWriteDTO> data = myPagePostService.readPostWrite(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("내가 쓴 글이 출력되었습니다", data));
    }

    //    마이페이지 내가 좋아요한 글
    @GetMapping("read-post-like")
    public ResponseEntity<ApiResponseDTO> readPostLike(@RequestParam Long id){
        log.info("내가 좋아요한 글을 불러옵니다");
        log.info("출력: {}", myPagePostService.readPostLike(id));
        List<MyPagePostLikeDTO> data = myPagePostService.readPostLike(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("내가 좋아요한 글이 출력되었습니다", data));
    }

    //    마이페이지 내가 쓴 댓글과 대댓글
    @GetMapping("read-post-comment")
    public ResponseEntity<ApiResponseDTO> readPostComment(@RequestParam Long id){
        log.info("내가 쓴 댓글과 대댓글을 불러옵니다");
        log.info("출력: {}", myPagePostService.readPostComment(id));
        List<MyPagePostCommentDTO> data = myPagePostService.readPostComment(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("내가 쓴 댓글과 대댓글이 출력되었습니다", data));
    }

    //    마이페이지 내가 임시저장한 글
    @GetMapping("read-post-save")
    public ResponseEntity<ApiResponseDTO> readPostSave(@RequestParam Long id){
        log.info("내가 임시저장한 글을 불러옵니다");
        log.info("출력: {}", myPagePostService.readPostSave(id));
        List<MyPagePostSaveDTO> data = myPagePostService.readPostSave(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("내가 임시저장한 글이 출력되었습니다", data));
    }

    //    마이페이지 내가 최근에 본 글
    @GetMapping("read-post-recent")
    public ResponseEntity<ApiResponseDTO> readPostRecent(@RequestParam Long id){
        log.info("내가 최근에 본 글을 불러옵니다");
        log.info("출력: {}", myPagePostService.readPostRecent(id));
        List<MyPagePostRecentDTO> data = myPagePostService.readPostRecent(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("내가 최근에 본 글이 출력되었습니다", data));
    }
    //    내가 작성한 글 삭제
    @DeleteMapping("delete-post-write")
    public ResponseEntity<ApiResponseDTO> deletePostWrite(@RequestParam Long id){
        log.info("내가 작성한 글을 삭제합니다");
        postService.withdraw(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("내가 작성한 글이 삭제되었습니다"));
    }

    //    내가 좋아요한 글 삭제
    @DeleteMapping("delete-post-like")
    public ResponseEntity<ApiResponseDTO> deletePostLike(@RequestParam Long id){
        log.info("나의 좋아요를 취소합니다");
        myPagePostService.deletePostLike(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("나의 좋아요가 취소되었습니다"));
    }

    //    내가 단 댓글 삭제
    @DeleteMapping("delete-post-comment")
    public ResponseEntity<ApiResponseDTO> deletePostComment(@RequestParam Long id){
        log.info("내가 단 댓글을 삭제합니다");
        postService.deleteComment(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("내가 단 댓글이 삭제되었습니다"));
    }

    //    내가 단 댓글 삭제
    @DeleteMapping("delete-post-reply")
    public ResponseEntity<ApiResponseDTO> deletePostReply(@RequestParam Long id){
        log.info("내가 단 대댓글을 삭제합니다");
        postService.deleteReplyById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("내가 단 댓글이 삭제되었습니다"));
    }

    //    내가 임시저장한 글 삭제
    @DeleteMapping("delete-post-save")
    public ResponseEntity<ApiResponseDTO> deletePostSave(@RequestParam Long id){
        log.info("내가 임시저장한 글을 삭제합니다");
        postService.deleteDraft(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("내가 임시저장한 글이 삭제되었습니다"));
    }

    //    내가 최근에 본 글 삭제
    @DeleteMapping("delete-post-recent")
    public ResponseEntity<ApiResponseDTO> deletePostRecent(@RequestParam Long id){
        log.info("내가 최근에 본 글을 불러옵니다");
        myPagePostService.deletePostRecent(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("내가 최근에 본 글이 삭제되었습니다"));
    }
}
