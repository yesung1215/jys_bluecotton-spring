package com.app.bluecotton.api.mypage;

import com.app.bluecotton.domain.dto.ApiResponseDTO;
import com.app.bluecotton.domain.dto.MyPageSomCheckDTO;
import com.app.bluecotton.domain.vo.som.SomReviewVO;
import com.app.bluecotton.domain.vo.som.SomVO;
import com.app.bluecotton.service.MyPageSomService;
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
public class MyPageSomApi {

    private final MyPageSomService myPageSomService;

    //    마이페이지 솜 인증 추가
    @PostMapping("insert-som-check")
    public ResponseEntity<ApiResponseDTO> insertSomCheckWithImages(@RequestBody MyPageSomCheckDTO myPageSomCheckDTO) {
        log.info("솜 인증를 불러옵니다");
        myPageSomService.insertSomCheckWithImages(myPageSomCheckDTO);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("솜 인증를 호출했습니다"));
    }

    //    마이페이지 솜 리뷰 추가
    @PostMapping("insert-som-review")
    public ResponseEntity<ApiResponseDTO> insertSomReview(@RequestBody SomReviewVO somReviewVO) {
        log.info("솜 리뷰를 불러옵니다");
        myPageSomService.insertSomReview(somReviewVO);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("솜 리뷰를 호출했습니다"));
    }
}
