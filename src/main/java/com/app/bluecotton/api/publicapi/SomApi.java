package com.app.bluecotton.api.publicapi;

import com.app.bluecotton.domain.dto.ApiResponseDTO;
import com.app.bluecotton.domain.dto.SomReadResponseDTO;
import com.app.bluecotton.domain.dto.SomResponseDTO;
import com.app.bluecotton.domain.vo.som.SomJoinVO;
import com.app.bluecotton.domain.vo.som.SomVO;
import com.app.bluecotton.service.MemberService;
import com.app.bluecotton.service.SomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/som/*")
public class SomApi {
    private final SomService somService;

    //  솜 등록
    @PostMapping("register")
    public ResponseEntity<ApiResponseDTO> registerSom(@RequestBody SomVO somVO) {
        SomJoinVO somJoinVO = new SomJoinVO();
        somService.registerSom(somVO);
        somJoinVO.setSomId(somVO.getId());
        somJoinVO.setMemberId(somVO.getMemberId());
        somService.registerSomJoin(somJoinVO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.of("솜이 등록되었습니다", somVO));
    }

    @PostMapping("join")
    public ResponseEntity<ApiResponseDTO> joinSom(@RequestBody SomJoinVO somJoinVO) {
        somService.registerSomJoin(somJoinVO);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("솜 참여가 완료되었습니다."));
    }

    //  솜 상세 조회
    @GetMapping("read")
    public ResponseEntity<ApiResponseDTO> getSomById(@RequestParam Long somId, @RequestParam(defaultValue = "") String memberEmail) {
        SomResponseDTO data = somService.findById(somId, memberEmail);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("솜 상세페이지를 불러왔습니다",data));
    }

    //  솜 카테고리별 조회
    @GetMapping("category")
    public ResponseEntity<ApiResponseDTO> getSomByCategory(
            @RequestParam(defaultValue = "all") String somCategory,
            @RequestParam(defaultValue = "all") String somType,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "") String memberEmail
    ) {
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> resultData = new HashMap<>();
        String message = null;
        params.put("somCategory", somCategory);
        params.put("somType", somType);
        params.put("page", page);
        params.put("memberEmail", memberEmail);
        if (somCategory.equals("all") && somType.equals("all")) {
            message = "솜 전체를 불러왔습니다.";
        } else {
            message = "솜을 조건에 맞게 분류하여 불러왔습니다.";
        }
        List<SomResponseDTO> listData = somService.findByCategoryAndType(params);
        Integer maxPage = somService.selectSomMaxPage(params);
        resultData.put("somList", listData);
        resultData.put("maxPage", maxPage);
        log.info("category: {}", somCategory);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of(message, resultData));
    }

    //  솜 전체 조회
    @GetMapping("all")
    public ResponseEntity<ApiResponseDTO> getAllSom() {
        List<SomResponseDTO> data = somService.findAllSom();
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
