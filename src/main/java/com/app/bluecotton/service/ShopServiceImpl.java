package com.app.bluecotton.service;

import com.app.bluecotton.domain.dto.*;
import com.app.bluecotton.repository.ShopDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {


    private final ShopDAO shopDAO;

    @Override
    public List<ProductListResponseDTO> getProductByFilter(Map<String, Object> filterParams) {
        return shopDAO.findProductsByFilter(filterParams);
    }

    @Override
    public ProductDetailResponseDTO getProductDetailHeader(Long id, Long memberId) {
        return shopDAO.findProductDetailHeader(id, memberId);
    }

    @Override
    public ProductInfoDetailResponseDTO getProductDetailInfo(Long id) {
        return shopDAO.findProductDetailInfo(id);
    }

    @Override
    public List<ProductReviewDetailResponseDTO> getProductReviewDetail(Map<String, Object> reviewParams) {
        return shopDAO.findProductReviewDetail(reviewParams);
    }

    @Override
    public ProductReviewStatsResponseDTO getProductReviewStats(Long id) {
        return shopDAO.findProductReviewStats(id);
    }

    // 찜하기 토글
    @Override
    @Transactional
    public void toggleLike(Long memberId, Long productId) {
        Integer count = shopDAO.findLikeCount(memberId, productId);

        // 찜한 상품이 있을 때
        if (count > 0) {
            // 찜 삭제
            shopDAO.deleteLikedProduct(memberId, productId);
        }
        // 찜한 상품 없을 때
        else {
            // 찜 추가
            shopDAO.insertMyLikedProduct(memberId, productId);
        }
    }

    @Override
    public List<ProductListResponseDTO> getLikedProducts(Long memberId) {
        return shopDAO.findLikedProducts(memberId);
    }

    @Override
    public List<MyReviewListDTO> getMyReviews(Long id) {
        return shopDAO.findMyReviews(id);
    }

    @Override
    public void modifyMyReview(Map<String, Object> modifyReview) {
        shopDAO.updateMyReview(modifyReview);
    }

    @Override
    public void deleteMyReview(Long id) {
        shopDAO.deleteMyReview(id);
    }

    @Override
    public List<MyPageOrderListDTO> getMyOrders(Long memberId) {
        return shopDAO.findMyOrders(memberId);
    }

    @Override
    public Map<String, Object> getReviewModal(Long productId) {
        return shopDAO.getReviewModal(productId);
    }

    @Override
    public void insertMyReview(MyPageReviewWriteDTO myPageReviewWriteDTO) {
        shopDAO.insertMyReview(myPageReviewWriteDTO);
    }

    @Override
    public void insertMyReviewImage(MyPageReviewWriteDTO myPageReviewWriteDTO) {
        shopDAO.insertMyReviewImage(myPageReviewWriteDTO);
    }

    @Override
    public List<MyPageDeliveryListDTO> getMyDeliveryList(Long memberId) {
        return shopDAO.findMyDeliveryList(memberId);
    }

    @Override
    public void deleteMyDeliveryProduct(Long id) {
        shopDAO.deleteMyDeliveryProduct(id);
    }


}




