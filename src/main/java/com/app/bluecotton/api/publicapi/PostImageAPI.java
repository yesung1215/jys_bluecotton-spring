package com.app.bluecotton.api.publicapi;

import com.app.bluecotton.domain.dto.ApiResponseDTO;
import com.app.bluecotton.domain.dto.SomImageUpdateDTO;
import com.app.bluecotton.domain.dto.post.PostImageUpdateDTO;
import com.app.bluecotton.domain.vo.post.PostImageVO;
import com.app.bluecotton.domain.vo.som.SomImageVO;
import com.app.bluecotton.service.PostImageService;
import com.app.bluecotton.service.SomImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/post-image/*")
public class PostImageAPI {

    private final PostImageService postImageService;

    @PostMapping("insert")
    public ResponseEntity<ApiResponseDTO> insertPostImage(@RequestBody PostImageVO postImageVO) {
        postImageService.createPostImageTemp(postImageVO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.of("게시판 이미지 임시 등록 완료", postImageVO));
    }

    @PutMapping("update")
    public ResponseEntity<ApiResponseDTO> updatePostImage(@RequestBody PostImageUpdateDTO postImageUpdateDTO) {
        log.info("받은 PostId: {}", postImageUpdateDTO.getPostId());
        log.info("받은 ImageIds: {}", postImageUpdateDTO.getPostImageIds());
        log.info("이미지 업데이트 완료: {}", postImageUpdateDTO.toString().replace("\n", ""));
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.of("게시판 이미지 등록 완료"));
    };
}
