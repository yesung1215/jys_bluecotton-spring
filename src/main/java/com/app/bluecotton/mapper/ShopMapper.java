package com.app.bluecotton.mapper;

import com.app.bluecotton.domain.dto.*;
import com.app.bluecotton.domain.vo.shop.ProductReviewReportVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ShopMapper {

    // 메인 페이지 상품 조건 조회
    public List<ProductListResponseDTO> selectProductsByFilter(Map<String,Object> filterParams);



    // 메인 페이지 상품 찜하기 추가
    public void insertMyLikedProduct(@Param("memberId") Long memberId , @Param("productId") Long productId);


    // 상세 페이지 상품 상단 조회
    public ProductDetailResponseDTO selectProductDetailHeader(Map<String,Object> likeParams);


    // 상세 페이지 상품 상단 찜하기 로직
    public ProductDetailResponseDTO selectProductDetailHeaderLike(Map<String,Object> like);


    // 상세 페이지 상품 정보 조회
    public ProductInfoDetailResponseDTO selectProductDetailInfo(Long id);

    // 상세 페이지 "상품 리뷰" 조회
    public List<ProductReviewDetailResponseDTO> selectProductReviewDetail(Map<String,Object> reviewParams);

    // 상세 페이지 "리뷰 평점" 조회
    public ProductReviewStatsResponseDTO selectProductReviewStats(Long id);


    // 마이페이지(샵) 찜한 상품 조회
    public List<ProductListResponseDTO> selectMyLikedProducts(Long memberId);

    // 마이페이지(샵) 찜한 상품 삭제
    // 매개변수 여러개 일때는 이름 지정해야함
    public void deleteMyLikedProduct(@Param("memberId") Long memberId , @Param("productId") Long productId);


    // 찜 상태 확인
    public Integer selectLikeCount(@Param("memberId") Long memberId , @Param("productId") Long productId);

    // 마이페이지(샵) 마이리뷰 조회
    public List<MyReviewListDTO> selectMyReview(@Param("memberId") Long memberId);

    // 마이페이지(샵) 마이리뷰 수정
    public void updateMyReview(Map<String,Object> updateReview);

    // 마이페이지(샵) 마이리뷰 삭제
    public void deleteMyReview(Long id);



    // 마이리뷰 : 기존 리뷰 이미지 전부 삭제
    public void deleteMyReviewImage(Long id);

    // 마이리뷰 : 수정 시 새 이미지 1장 등록
    public void insertMyReviewImageOnUpdate(Map<String,Object> updateReview);


    // 마이페이지(샵) 구매내역 전체조회
    public List<MyPageOrderListDTO> selectPurchaseList(@Param("memberId") Long memberId);

    // 마이페이지 구매내역 등록 모달
    public Map<String, Object> selectReviewModal(@Param("productId") Long productId);

    // 마이페이지 구매내역 내용 추가
    public void insertMyReview(MyPageReviewWriteDTO myPageReviewWriteDTO);

    // 마이페이지 구매내역 이미지 추가
    public void insertMyReviewImage(MyPageReviewWriteDTO myPageReviewWriteDTO);


    // 마이페이지 배송현황 전체 조회
    public List<MyPageDeliveryListDTO> selectMyDeliveryList(@Param("memberId") Long memberId);

    // 마이페이지 배송현황 구매 취소
    public void deleteMyDeliveryProduct(Long id);

    // 마이페이지 배송현황 구매 취소 (ORDER_ID 삭제)
    public void deletePaymentByOrderId(Long id);

    // 마이페이지 배송현황 구매 취소 (PAYMENT_SOCIAL_ID 삭제)
    public void deletePaymentSocialByPaymentId(Long id);


    // 구매하기 유효성 검사
    public int existProductReview(Map<String,Object> reviewParams);


    // 상품 리뷰 댓글 신고
    public void productReviewReport(ProductReviewReportVO productReviewReportVO);

    // 상품 리뷰 댓글 신고 중복 체크
    public int checkProductReviewReportExists(Map<String,Object> reviewParams);


    // 리뷰 댓글 도움돼요 상태 여부 조회
    public ProductReviewRecommendDTO selectProductReviewRecommend(Map<String,Object> recommend);


}
