package com.app.bluecotton.repository;

import com.app.bluecotton.domain.dto.*;
import com.app.bluecotton.domain.vo.shop.ProductReviewReportVO;
import com.app.bluecotton.mapper.ShopMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ShopDAO {

    private final ShopMapper shopMapper;

    // 메인 페이지 상품 조건 조회
    public List<ProductListResponseDTO> findProductsByFilter(Map<String,Object> filterParams) {
        return shopMapper.selectProductsByFilter(filterParams);
    }

    // 메인 페이지 상품 찜하기 추가
    public void insertMyLikedProduct(Long memberId, Long productId){
         shopMapper.insertMyLikedProduct(memberId, productId);
    }


    // 상세 페이지 상단 조회
    public ProductDetailResponseDTO findProductDetailHeader(Long id, Long memberId) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("memberId", memberId);

        return shopMapper.selectProductDetailHeader(params);
    }

    // 상세 페이지 상품 상단 찜하기 로직
    public ProductDetailResponseDTO findProductDetailHeaderLike(Long productId, Long memberId) {
        Map<String, Object> likeParams = new HashMap<>();
        likeParams.put("productId", productId);
        likeParams.put("memberId", memberId);
        return shopMapper.selectProductDetailHeaderLike(likeParams);
    }

    // 상세 페이지 상품 정보 조회
    public ProductInfoDetailResponseDTO findProductDetailInfo(Long id){
        return shopMapper.selectProductDetailInfo(id);
    }

    // 상세 페이지 "상품 리뷰" 조회
    public List<ProductReviewDetailResponseDTO> findProductReviewDetail(Map<String,Object> reviewParams){
        return shopMapper.selectProductReviewDetail(reviewParams);
    }

    // 상세 페이지 "리뷰 평점" 조회
    public ProductReviewStatsResponseDTO findProductReviewStats(Long id){
        return shopMapper.selectProductReviewStats(id);
    }

    // 찜 상태 확인
    public Integer findLikeCount(@Param("memberId") Long memberId, @Param("productId") Long productId) {
        return shopMapper.selectLikeCount(memberId, productId);
    }

    // 마이페이지(샵) 찜한 상품 조회
    public List<ProductListResponseDTO> findLikedProducts(Long memberId){
        return shopMapper.selectMyLikedProducts(memberId);
    }

    // 마이페이지(샵) 찜한 상품 삭제
    public void deleteLikedProduct(Long memberId, Long productId){
        shopMapper.deleteMyLikedProduct(memberId, productId);
    }


    // 마이페이지(샵) 마이리뷰 조회
    public List<MyReviewListDTO> findMyReviews(Long id){
        return shopMapper.selectMyReview(id);
    }

    // 마이페이지(샵) 마이리뷰 수정
    public void updateMyReview(Map<String,Object> updateReview) {
        shopMapper.updateMyReview(updateReview);
    }

    // 마이페이지(샵) 마이리뷰 삭제
    public void deleteMyReview(Long id){
        shopMapper.deleteMyReview(id);
    }


    // 마이리뷰 : 기존 리뷰 이미지 전부 삭제
    public void deleteMyReviewImage(Long id){
        shopMapper.deleteMyReviewImage(id);
    }

    // 마이리뷰 : 수정 시 새 이미지 1장 등록
    public void insertMyReviewImageOnUpdate(Map<String,Object> updateReview) {
        shopMapper.insertMyReviewImageOnUpdate(updateReview);
    }

    // ---- 구매 내역 ----
    // 마이페이지(샵) 구매내역 전체조회
    public List<MyPageOrderListDTO> findMyOrders(Long memberId){
        return shopMapper.selectPurchaseList(memberId);
    }

    // 마이페이지(샵) 구매내역 리뷰 모달 조회
    public Map<String, Object> getReviewModal(Long productId){
        return shopMapper.selectReviewModal(productId);
    }

    // 마이페이지(샵) 구매내역 리뷰 작성
    public void insertMyReview(MyPageReviewWriteDTO myPageReviewWriteDTO) {
        shopMapper.insertMyReview(myPageReviewWriteDTO);
    }

    // 마이페이지(샵) 구매내역 리뷰 이미지 작성
    public void insertMyReviewImage(MyPageReviewWriteDTO myPageReviewWriteDTO) {
        shopMapper.insertMyReviewImage(myPageReviewWriteDTO);
    }

    // 마이페이지(샵) 배송현황 전체 조회
    public List<MyPageDeliveryListDTO> findMyDeliveryList(Long memberId){
        return shopMapper.selectMyDeliveryList(memberId);
    }

    // 마이페이지(샵) 배송현황 삭제
    public void deleteMyDeliveryProduct(Long id){
        shopMapper.deleteMyDeliveryProduct(id);
    }

    // 마이페이지(샵) 배송현황(ORDER_ID) 삭제
    public void deletePaymentByOrderId(Long id){
        shopMapper.deletePaymentByOrderId(id);
    }

    // 마이페이지 배송현황 구매 취소 (PAYMENT_SOCIAL_ID 삭제)
    public void deletePaymentSocialByPaymentId(Long id){
        shopMapper.deletePaymentSocialByPaymentId(id);
    }


    // 구매하기 유효성 검사
    public int existProductReview(Long productId, Long memberId) {
        Map<String, Object> params = new HashMap<>();
        params.put("productId", productId);
        params.put("memberId", memberId);
        return shopMapper.existProductReview(params);
    }


    // 상품 리뷰 댓글 신고하기
    public void reportProductReview(ProductReviewReportVO productReviewReportVO) {
        shopMapper.productReviewReport(productReviewReportVO);
    }

    // 상품 리뷰 댓글 신고 중복 체크
    public boolean checkProductReviewReportExists(Long productReviewId, Long memberId) {
        Map<String, Object> params = new HashMap<>();
        params.put("productReviewId", productReviewId);
        params.put("memberId", memberId);

        return shopMapper.checkProductReviewReportExists(params) > 0;
    }



}
