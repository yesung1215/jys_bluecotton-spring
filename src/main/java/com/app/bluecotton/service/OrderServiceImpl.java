package com.app.bluecotton.service;


import com.app.bluecotton.domain.dto.OrderCartDTO;
import com.app.bluecotton.domain.dto.OrderCheckoutDTO;
import com.app.bluecotton.domain.dto.OrderDTO;
import com.app.bluecotton.domain.dto.OrderItemDTO;
import com.app.bluecotton.domain.vo.shop.OrderVO;
import com.app.bluecotton.mapper.OrderMapper;
import com.app.bluecotton.repository.CartDAO;
import com.app.bluecotton.repository.OrderDAO;
import com.app.bluecotton.repository.ShopDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {


    private final OrderDAO orderDAO;
    private final CartDAO cartDAO;
    private final ShopDAO shopDAO;
    private final OrderMapper orderMapper;

    @Override
    public Long addOrder(OrderDTO orderDTO) {

        orderDAO.addOrder(orderDTO);
        return orderDTO.getId();
    }

    @Override
    public Long addOrderCart(OrderCheckoutDTO orderCheckoutDTO) {

        Long memberId = orderCheckoutDTO.getMemberId();
        List<OrderItemDTO> items = orderCheckoutDTO.getItems();

        Long lastOrderId = null;
        if(items == null || items.isEmpty()) {
            throw  new IllegalArgumentException("주문할 상품이 존재하지 않습니다.");
        }

        for(OrderItemDTO orderItemDTO : items) {
            Long unitPrice = orderDAO.selectProductPriceById(orderItemDTO.getProductId());

            if(unitPrice == null || unitPrice <= 0) {
                log.error("유효하지 않은 가격");
                throw  new IllegalArgumentException("유효하지 않거나 0원인 상품입니다");
            }

            Long calTotalPrice = unitPrice * orderItemDTO.getQuantity();

            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setMemberId(memberId);
            orderDTO.setProductId(orderItemDTO.getProductId());
            orderDTO.setQuantity(orderItemDTO.getQuantity());
            orderDTO.setOrderStatus('N');
            orderDTO.setTotalPrice(calTotalPrice);

            orderDAO.addOrder(orderDTO);

            lastOrderId = orderDTO.getId();
        }

        return lastOrderId;
    }

    @Override
    public List<OrderVO> selectAllOrders(Long memberId) {
        return orderDAO.selectAllOrders(memberId);
    }

    @Override
    public Optional<OrderVO> selectOrderById(Long id, Long memberId) {
        return orderDAO.selectOrderById(id, memberId);
    }

    @Override
    public void updateOrder(OrderVO orderVO) {
        orderDAO.updateOrder(orderVO);
    }

    @Override
    public void deleteOrder(Long id, Long memberId) {
        orderDAO.deleteOrder(id, memberId);
    }

    @Override
    public void clearCartAfterOrder(Long memberId) {
        // 1) 주문 테이블에서 카트 FK 끊기 (ORDER_STATUS='Y' 대상)
        orderDAO.detachOrderFromCart(memberId);
        // 2) 해당 회원 장바구니 전체 삭제
        cartDAO.deleteAllByMember(memberId);
    }

}
