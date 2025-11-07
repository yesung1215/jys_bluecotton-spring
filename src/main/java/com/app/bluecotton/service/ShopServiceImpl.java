package com.app.bluecotton.service;

import com.app.bluecotton.domain.dto.ProductDetailResponseDTO;
import com.app.bluecotton.domain.dto.ProductInfoDetailResponseDTO;
import com.app.bluecotton.domain.dto.ProductListResponseDTO;
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
    public List<ProductListResponseDTO> getProductByFilter(Map<String, Object> params) {
        return shopDAO.findProductsByFilter(params);
    }

    @Override
    public ProductDetailResponseDTO getProductDetailHeader(Long id) {
        return shopDAO.findProductDetailHeader(id);
    }

    @Override
    public ProductInfoDetailResponseDTO getProductDetailInfo(Long id) {
        return shopDAO.findProductDetailInfo(id);
    }

}
