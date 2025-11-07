package com.app.bluecotton.api.publicapi;

import com.app.bluecotton.domain.dto.ApiResponseDTO;
import com.app.bluecotton.domain.dto.SomResponseDTO;
import com.app.bluecotton.domain.vo.som.SomVO;
import com.app.bluecotton.service.MemberService;
import com.app.bluecotton.service.SomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/som/*")
public class SomApi {
    private final SomService somService;

    //  솜 등록
    @PostMapping("register")
    public ResponseEntity<ApiResponseDTO> registerSom(@RequestBody SomVO somVO) {
        somService.registerSom(somVO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.of("솜이 등록되었습니다"));
    }

    //  솜 상세 조회
    @PostMapping("read")
    public ResponseEntity<ApiResponseDTO> getSomById(@RequestParam Long somId) {
        SomResponseDTO data = somService.findById(somId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("솜 상세페이지를 불러왔습니다",data));
    }

    //  솜 카테고리별 조회
    @PostMapping("category")
    public ResponseEntity<ApiResponseDTO> getSomByCategory(@RequestParam String somCategory) {
        List<SomVO> data = somService.findByCategory(somCategory);
        log.info("category: {}", somCategory);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("솜 카테고리별로 불러왔습니다", data));
    }

    //  솜 전체 조회
    @GetMapping("all")
    public ResponseEntity<ApiResponseDTO> getAllSom() {
        List<SomVO> data = somService.findAllSom();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("솜 전체를 조회했습니다", data));
    }

    //  솜 전체 주소 조회
    @GetMapping("address")
    public ResponseEntity<ApiResponseDTO> getSomByAddress() {
        List<String> data = somService.findAllAddress();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("솜 주소를 불러왔습니다", data));
    }

    //  솜 좋아요
    @PutMapping("like")
    public ResponseEntity<ApiResponseDTO> likeSom(@RequestParam Long somId) {
        somService.addLike(somId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("솜 좋아요가 증가했습니다"));
    }

    //  솜 삭제
    @DeleteMapping("withdraw")
    public ResponseEntity<ApiResponseDTO> withdrawSom(@RequestParam Long somId) {
        somService.withdraw(somId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponseDTO.of("솜 이 삭제되었습니다"));
    }



}
