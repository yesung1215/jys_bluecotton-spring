package com.app.bluecotton.api.privateapi;

import com.app.bluecotton.domain.dto.*;
import com.app.bluecotton.service.PaymentService;
import com.app.bluecotton.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("private/mypage/myshop/*")
public class MyPageShopApi {

    final ShopService shopService;
    private final PaymentService paymentService;

    @GetMapping("like/{memberId}")
    public ResponseEntity<ApiResponseDTO> getLikedProducts(@PathVariable Long memberId){
        List<ProductListResponseDTO> likedProducts = shopService.getLikedProducts(memberId);
        log.info("찜한 상품 조회 요청 : {}",  likedProducts);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("찜한 상품 조회 성공", likedProducts));
    }


    @GetMapping("review/{memberId}")
    public ResponseEntity<ApiResponseDTO> getMyReviews(@PathVariable Long memberId){
        log.info("마이리뷰 조회 요청 들어옴", memberId);

        List<MyReviewListDTO> myReviews = shopService.getMyReviews(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("마이리뷰 조회 성공", myReviews));
    }

    // 마이리뷰 수정
    @PutMapping("review/{reviewId}")
    public ResponseEntity<ApiResponseDTO> modifyMyReview(@PathVariable Long reviewId, @RequestBody Map<String,Object> modifyReview){
        modifyReview.put("reviewId", reviewId);
        shopService.modifyMyReview(modifyReview);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("마이리뷰 수정 성공"));
    }

    @DeleteMapping("review/{reviewId}")
    public ResponseEntity<ApiResponseDTO> deleteMyReview(@PathVariable Long reviewId){
        shopService.deleteMyReview(reviewId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("마이리뷰 삭제 성공", reviewId));
    }

    @GetMapping("order")
    public ResponseEntity<ApiResponseDTO<List<MyShopOrderDTO>>> getMyShopOrders(
            @RequestParam Long memberId
    ) {
        log.info("[MyShopApi] 구매내역 조회 memberId={}", memberId);
        List<MyShopOrderDTO> list = paymentService.findCompletedOrdersByMemberId(memberId);
        return ResponseEntity.ok(
                ApiResponseDTO.of("구매내역 전체 조회 성공", list)
        );
    }


    @GetMapping("review/modal")
    public ResponseEntity<ApiResponseDTO> getMyReviewModal(@RequestParam Long productId){
        log.info("리뷰 모달 조회", productId);
        Map<String, Object> myModal = shopService.getReviewModal(productId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("리뷰 모달 조회 성공",  myModal));
    }

    @PostMapping("review")
    public ResponseEntity<ApiResponseDTO> createMyReview(@RequestBody MyPageReviewWriteDTO myPageReviewWriteDTO) {

        shopService.writeReview(myPageReviewWriteDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.of("리뷰 등록 성공"));
    }

    @GetMapping("delivery/{memberId}")
    public ResponseEntity<ApiResponseDTO> getMyDeliveryList(@PathVariable Long memberId){
        List<MyPageDeliveryListDTO> deliveryList = shopService.getMyDeliveryList(memberId);
        log.info("배송현황 전체 조회 성공{}", deliveryList);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("배송현황",  deliveryList));
    }


    @DeleteMapping("delivery/{id}")
    public ResponseEntity<ApiResponseDTO> deleteMyDeliveryList(@PathVariable Long id){
        log.info("구매 취소 성공");
        shopService.deleteMyDeliveryProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("구매 취소 성공"));
    }


    @GetMapping("/review/exist/{productId}/{memberId}")
    public ResponseEntity<ApiResponseDTO> existMyReview(@PathVariable Long productId, @PathVariable Long memberId){
        log.info("리뷰 유효성 검사 성공");
        int result = shopService.existProductReview(productId, memberId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("리뷰 작성 여부", result));
    }


    // 찜하기 토글
    @PostMapping("like/toggle")
    public ResponseEntity<ApiResponseDTO> toggleLike(@RequestBody Map<String, Long> toggle) {
        Long memberId = toggle.get("memberId");
        Long productId = toggle.get("productId");
        shopService.toggleLike(memberId, productId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("찜하기 토글 성공"));
    }



}
