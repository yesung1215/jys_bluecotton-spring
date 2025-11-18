package com.app.bluecotton.api.privateapi;

import com.app.bluecotton.domain.dto.ApiResponseDTO;
import com.app.bluecotton.domain.dto.MemberResponseDTO;
import com.app.bluecotton.domain.vo.shop.ProductReviewReportVO;
import com.app.bluecotton.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/private/shop/*")
@RequiredArgsConstructor
@Slf4j
public class ShopPrivateApi {

    private final ShopService shopService;

    // 상품 리뷰 댓글 신고
    @PostMapping("read/review/report")
    public ResponseEntity<ApiResponseDTO> createProductReview(
            @RequestBody ProductReviewReportVO productReviewReportVO,
            @AuthenticationPrincipal MemberResponseDTO currentMember){

        productReviewReportVO.setMemberId(currentMember.getId());
        shopService.reportProductReview(productReviewReportVO);
        return ResponseEntity.status(HttpStatus.CREATED).body((ApiResponseDTO.of("댓글 신고 완료", productReviewReportVO)));
    }

}
