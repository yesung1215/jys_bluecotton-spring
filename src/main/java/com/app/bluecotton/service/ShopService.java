package com.app.bluecotton.service;

import com.app.bluecotton.domain.dto.ProductDetailResponseDTO;
import com.app.bluecotton.domain.dto.ProductInfoDetailResponseDTO;
import com.app.bluecotton.domain.dto.ProductListResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface ShopService {

    // 상품 조건 조회
    public List<ProductListResponseDTO> getProductByFilter(Map<String,Object> params);

    // 상세 페이지 상단 조회
    public ProductDetailResponseDTO getProductDetailHeader(Long id);

    // 상세 페이지 상품 정보 조회
    public ProductInfoDetailResponseDTO getProductDetailInfo(Long id);


}
