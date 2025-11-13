package com.app.bluecotton.repository;


import com.app.bluecotton.domain.dto.*;
import com.app.bluecotton.domain.vo.shop.OrderVO;
import com.app.bluecotton.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderDAO {

    private final OrderMapper orderMapper;

    public void addOrder(OrderDTO orderDTO) {
        orderMapper.insertSingle(orderDTO);
    }

    public void addOrderCart(OrderCartDTO orderCartDTO) {
        orderMapper.insertCartOrder(orderCartDTO);
    }

    public Long selectProductPriceById(Long productId) {
        return orderMapper.selectProductPriceById(productId);
    }

    public Long createIntegratedOrder(OrderCheckoutDTO orderCheckoutDTO) {
        orderMapper.insertOrderHeader(orderCheckoutDTO);

        Long orderId = orderCheckoutDTO.getId();

        List<OrderItemDTO> items = orderCheckoutDTO.getItems();
        if(items!=null && items.isEmpty()){
            orderMapper.insertOrderHeader(orderId, items);
        }
        return orderId;
    }

    public List<OrderVO> selectAllOrders(Long memberId) {
        return orderMapper.selectAllByMemberId(memberId);
    }

    public Optional<OrderVO> selectOrderById(Long id, Long memberId) {
        return orderMapper.selectByMemberIdAndOrderId(id, memberId);
    }

    public List<OrderDetailDTO> selectOrderDetails(Long id, Long memberId) {
        // OrderMapper의 새로운 메서드를 호출합니다.
        // 이 쿼리는 단일 주문이라도 리스트 형태로 반환하는 것이 일반적입니다.
        return orderMapper.selectOrderDetailsByMemberIdAndOrderId(id, memberId);
    }

    public void updateOrder(OrderVO orderVO) {
        orderMapper.updateOrderStatus(orderVO);
    }

    public void deleteOrder(Long id, Long memberId) {
        orderMapper.delete(id, memberId);
    }

    public void  detachOrderFromCart(Long memberId) {
        orderMapper.detachOrderFromCart(memberId);
    }
}
