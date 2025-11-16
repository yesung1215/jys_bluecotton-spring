package com.app.bluecotton.api.publicapi;

import com.app.bluecotton.domain.dto.ApiResponseDTO;
import com.app.bluecotton.domain.dto.SomImageUpdateDTO;
import com.app.bluecotton.domain.vo.som.SomImageVO;
import com.app.bluecotton.repository.SomImageDAO;
import com.app.bluecotton.service.SomImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/som-image/*")
public class SomImageAPI {

    private final SomImageService somImageService;

    @PostMapping("insert")
    public ResponseEntity<ApiResponseDTO> insertSomImage(@RequestBody SomImageVO somImageVO) {
        somImageService.createSomImageTemp(somImageVO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.of("솜 이미지 임시 등록 완료", somImageVO));
    }

    @PutMapping("update")
    public ResponseEntity<ApiResponseDTO> updateSomImage(@RequestBody SomImageUpdateDTO somImageUpdateDTO) {
        somImageService.updateInsertSomImage(somImageUpdateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.of("솜 이미지 등록 완료"));
    };
}
