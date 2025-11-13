package com.app.bluecotton.mapper;

import com.app.bluecotton.domain.dto.*;
import com.app.bluecotton.domain.vo.shop.OrderVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface OrderMapper {

    public void insertSingle(OrderDTO orderDTO);

    public void insertCartOrder(OrderCartDTO orderCartDTO);

    public void insertOrderHeader(OrderCheckoutDTO orderCheckoutDTO);

    public List<OrderDetailDTO> selectOrderDetailsByMemberIdAndOrderId(Long id, Long memberId);
    public void insertOrderItems(Long orderId, List<OrderItemDTO> items);

    public Long selectProductPriceById(Long productId);

    public List<OrderVO> selectAllByMemberId (Long memberId);

    public Optional<OrderVO> selectByMemberIdAndOrderId (Long id, Long memberId);

    public void updateOrderStatus(OrderVO orderVO);

    public void delete(Long id, Long memberId);

    public void detachOrderFromCart(Long memberId);

    void insertOrderHeader(Long orderId, List<OrderItemDTO> items);
}
