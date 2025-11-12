package com.app.bluecotton.repository;


import com.app.bluecotton.domain.dto.OrderCartDTO;
import com.app.bluecotton.domain.dto.OrderCheckoutDTO;
import com.app.bluecotton.domain.dto.OrderDTO;
import com.app.bluecotton.domain.dto.OrderItemDTO;
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
