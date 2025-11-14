package com.app.bluecotton.api.privateapi;

import com.app.bluecotton.domain.dto.ApiResponseDTO;
import com.app.bluecotton.domain.dto.MyPageSomCheckDTO;
import com.app.bluecotton.domain.dto.MyPageSomReviewDTO;
import com.app.bluecotton.domain.vo.som.SomReviewVO;
import com.app.bluecotton.domain.vo.som.SomVO;
import com.app.bluecotton.service.MyPageSomService;
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
public class MyPageSomApi {

    private final MyPageSomService myPageSomService;

    // 솜 정보 출력
    @GetMapping("read-som")
    public ResponseEntity<ApiResponseDTO> readSom(Long id) {
        log.info("솔로솜 및 파티솜 정보를 불러옵니다");
        log.info("출력: {}", myPageSomService.selectById(id));
        List<SomVO> data = myPageSomService.selectById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("솔로솜과 파티솜을 조회했습니다", data));
    }

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

    //    마이페이지 솜 인증 호출
    @GetMapping("read-som-check")
    public ResponseEntity<ApiResponseDTO> readSomCheck(@RequestParam Long id) {
        log.info("솜 인증를 불러옵니다");
        List<MyPageSomCheckDTO> data = myPageSomService.readSomCheck(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("솜 인증을 호출했습니다", data));
    }

    //    마이페이지 솜 리뷰 호출
    @GetMapping("read-som-review")
    public ResponseEntity<ApiResponseDTO> readSomReview(@RequestParam Long id) {
        log.info("솜 리뷰를 불러옵니다");
        List<MyPageSomReviewDTO> data =  myPageSomService.readSomReview(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("솜 리뷰를 호출했습니다", data));
    }

    //    마이페이지 랭크 호출
    @GetMapping("read-rank")
    public ResponseEntity<ApiResponseDTO> readRank(@RequestParam Long id) {
        log.info("랭크를 불러옵니다");
        Long data =  myPageSomService.readRank(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("랭크를 호출했습니다", data));
    }
}
