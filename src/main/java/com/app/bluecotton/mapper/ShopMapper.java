package com.app.bluecotton.mapper;

import com.app.bluecotton.domain.dto.ProductDetailResponseDTO;
import com.app.bluecotton.domain.dto.ProductInfoDetailResponseDTO;
import com.app.bluecotton.domain.dto.ProductListResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ShopMapper {

    // 메인 페이지 상품 조건 조회
    public List<ProductListResponseDTO> selectProductsByFilter(Map<String,Object> params);


    // 상세 페이지 상품 상단 조회
    public ProductDetailResponseDTO selectProductDetailHeader(Long id);

    // 상세 페이지 상품 정보 조회
    public ProductInfoDetailResponseDTO selectProductDetailInfo(Long id);

}
