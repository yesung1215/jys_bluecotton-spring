package com.app.bluecotton.service;

import com.app.bluecotton.domain.dto.OrderCartDTO;
import com.app.bluecotton.domain.dto.OrderCheckoutDTO;
import com.app.bluecotton.domain.dto.OrderDTO;
import com.app.bluecotton.domain.vo.shop.OrderVO;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    public Long addOrder(OrderDTO orderDTO);

    public Long addOrderCart(OrderCheckoutDTO orderCheckoutDTO);

    public List<OrderVO> selectAllOrders(Long memberId);

    public Optional<OrderVO> selectOrderById(Long id, Long memberId);

    public void updateOrder(OrderVO orderVO);

    public void deleteOrder(Long id, Long memberId);

    public void clearCartAfterOrder(Long memberId);


}
