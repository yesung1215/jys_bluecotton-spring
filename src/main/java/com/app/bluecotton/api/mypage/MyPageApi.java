package com.app.bluecotton.api.mypage;

import com.app.bluecotton.domain.dto.*;
import com.app.bluecotton.domain.vo.member.MemberVO;
import com.app.bluecotton.domain.vo.post.PostDraftVO;
import com.app.bluecotton.domain.vo.som.SomVO;
import com.app.bluecotton.service.MemberService;
import com.app.bluecotton.service.MyPagePostService;
import com.app.bluecotton.service.PostService;
import com.app.bluecotton.service.SomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/my-page/*")
public class MyPageApi {

    private final SomService somService;
    private final MemberService memberService;
    private final MyPagePostService myPagePostService;

    // 솜 정보 출력
    @GetMapping("read-som")
    public ResponseEntity<ApiResponseDTO> readSom() {
        log.info("솜 정보를 불러옵니다");
        log.info("출력: {}", somService.findAllSom());
        List<SomResponseDTO> data = somService.findAllSom();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("솜 전체를 조회했습니다", data));
    }

    // 회원정보 출력
    @GetMapping("read-member")
    public ResponseEntity<ApiResponseDTO> readMember(@RequestParam Long id ){
        log.info("회원정보를 불러옵니다");
        log.info("출력: {}", memberService.getMemberById(id));
        MemberResponseDTO data =  memberService.getMemberById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("회원정보가 출력되었습니다", data));
    }
    // 회원주소 출력
    @GetMapping("member-address")
    public ResponseEntity<ApiResponseDTO<Object>> readMemberAddress(){
        List<String> data = memberService.findAllMemberAddress();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("모든 회원의 주소를 조회했습니다", data));
    }

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
        myPagePostService.deletePostWrite(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("내가 작성한 글이 삭제되었습니다"));
    }

    //    내가 좋아요한 글 삭제
    @DeleteMapping("delete-post-like")
    public ResponseEntity<ApiResponseDTO> deletePostLike(@RequestParam Long id){
        log.info("내가 좋아요한 글을 삭제합니다");
        myPagePostService.deletePostLike(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("내가 좋아요한 글이 삭제되었습니다"));
    }

    //    내가 단 댓글 삭제
    @DeleteMapping("delete-post-comment")
    public ResponseEntity<ApiResponseDTO> deletePostComment(@RequestParam Long id){
        log.info("내가 단 댓글을 삭제합니다");
        myPagePostService.deletePostComment(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("내가 단 댓글이 삭제되었습니다"));
    }

    //    내가 단 댓글 삭제
    @DeleteMapping("delete-post-reply")
    public ResponseEntity<ApiResponseDTO> deletePostReply(@RequestParam Long id){
        log.info("내가 단 대댓글을 삭제합니다");
        myPagePostService.deletePostReply(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("내가 단 댓글이 삭제되었습니다"));
    }

    //    내가 임시저장한 글 삭제
    @DeleteMapping("delete-post-save")
    public ResponseEntity<ApiResponseDTO> deletePostSave(@RequestParam Long id){
        log.info("내가 임시저장한 글을 삭제합니다");
        myPagePostService.deletePostSave(id);
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
