package com.app.bluecotton.service;

import com.app.bluecotton.domain.dto.CartResponseDTO;
import com.app.bluecotton.domain.vo.shop.CartVO;
import com.app.bluecotton.exception.CartException;
import com.app.bluecotton.repository.CartDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartDAO cartDAO;

    @Override
    public void addCart(CartVO cartVO) {
        cartDAO.add(cartVO);
    }

    @Override
    public void updatePlusCart(CartVO cartVO) {
        Long memberId = cartVO.getMemberId();
        Long productId = cartVO.getProductId();
        cartDAO.increaseProduct(cartVO);
    }

    @Override
    public void updateMinusCart(CartVO cartVO) {
        Long memberId = cartVO.getMemberId();
        Long productId = cartVO.getProductId();
//        Optional<Integer> quantityOptional = cartDAO.selectQuantity(memberId, productId);
//
//        quantityOptional.ifPresentOrElse(quantity -> {
//            if(quantity <= 1) {
//                throw new CartException("상품 수량이 1입니다. 더이상 줄일 수 없습니다");
//            }
//            cartDAO.decreaseProduct(cartVO);
//        }, () -> {
//            throw new CartException("해당 상품이 없습니다");
//        });
//
        cartDAO.decreaseProduct(cartVO);

    }

    @Override
    public void deleteCart(Long memberId, Long productId) {
        cartDAO.deleteProduct(memberId, productId);
    }

    @Override
    public List<CartResponseDTO> getCartList(Long memberId) {
        return cartDAO.selectAllCart(memberId);
    }

    @Override
    public Optional<CartVO> selectCartQuantity(Long memberId, Long productId) {
        return cartDAO.selectQuantity(memberId, productId)
                .map(q -> {
                    CartVO cartVO = new CartVO();
                    cartVO.setMemberId(memberId);
                    cartVO.setProductId(productId);
                    cartVO.setQuantity(q);
                    return cartVO;
                });
    }

    @Override
    public void clearCart(Long memberId) {
        cartDAO.deleteAllByMember(memberId);
    }

    private CartVO defaultOne(Long memberId, Long productId) {
        CartVO vo = new CartVO();
        vo.setMemberId(memberId);
        vo.setProductId(productId);
        vo.setQuantity(1);
        return vo;
    }
}

