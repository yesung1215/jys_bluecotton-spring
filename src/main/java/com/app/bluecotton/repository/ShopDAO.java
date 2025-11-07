package com.app.bluecotton.repository;

import com.app.bluecotton.domain.dto.ProductDetailResponseDTO;
import com.app.bluecotton.domain.dto.ProductInfoDetailResponseDTO;
import com.app.bluecotton.domain.dto.ProductListResponseDTO;
import com.app.bluecotton.mapper.ShopMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ShopDAO {

    private final ShopMapper shopMapper;

    // 메인 페이지 상품 조건 조회
    public List<ProductListResponseDTO> findProductsByFilter(Map<String,Object> params){
        return shopMapper.selectProductsByFilter(params);
    }

    // 상세 페이지 상단 조회
    public ProductDetailResponseDTO findProductDetailHeader(Long id){
        return shopMapper.selectProductDetailHeader(id);
    }

    // 상세 페이지 상품 정보 조회
    public ProductInfoDetailResponseDTO findProductDetailInfo(Long id){
        return shopMapper.selectProductDetailInfo(id);
    }



}
