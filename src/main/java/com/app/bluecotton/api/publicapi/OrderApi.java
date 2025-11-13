package com.app.bluecotton.api.publicapi;


import com.app.bluecotton.domain.dto.*;
import com.app.bluecotton.domain.vo.shop.OrderVO;
import com.app.bluecotton.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/order/*")
public class OrderApi {

    private final OrderService orderService;

    @PostMapping("single")
    public ResponseEntity<ApiResponseDTO<Long>> addOrder(@RequestBody  OrderDTO orderDTO) {
        Long orderId = orderService.addOrder(orderDTO);
        log.info("orderId:{}",orderId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("주문하기 성공!", orderId));
    }

    @PostMapping("cart")
    public  ResponseEntity<ApiResponseDTO<Long>>  addOrderCart(@RequestBody OrderCheckoutDTO orderCheckoutDTO) {
        Long orderId = orderService.addOrderCart(orderCheckoutDTO);
        log.info("Integrated Order ID: {}", orderId);

        Long memberId = orderCheckoutDTO.getMemberId();
        orderService.clearCartAfterOrder(memberId);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("장바구니에서 주문하기 성공!", orderId));
    }

    @GetMapping("list")
    public ResponseEntity<ApiResponseDTO<List<OrderVO>>>  selectAllOrders(@RequestParam Long memberId) {
        List<OrderVO> orders = orderService.selectAllOrders(memberId);
        return ResponseEntity.ok(ApiResponseDTO.of("주문 리스트 확인 완료", orders));
    }

    @GetMapping("option")
    public ResponseEntity<ApiResponseDTO<List<OrderDetailDTO>>> selectOrderDetails(@RequestParam Long id, @RequestParam Long memberId) {

        // Service의 새 메서드를 호출하여 단가 정보가 포함된 List<OrderDetailDTO>를 받습니다.
        List<OrderDetailDTO> orderDetails = orderService.selectOrderDetailsById(id, memberId);

        // 프론트엔드가 배열을 받을 수 있도록 List 형태로 반환합니다.
        return ResponseEntity.ok(ApiResponseDTO.of("단일주문 상세 확인 완료", orderDetails));
    }

    @PutMapping("update")
    public ResponseEntity<ApiResponseDTO> updateOrder(@RequestBody OrderVO orderVO) {
        orderService.updateOrder(orderVO);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("주문 업데이트 완료"));
    }

    @DeleteMapping("delete")
    public ResponseEntity<ApiResponseDTO> deleteOrderById(@RequestParam Long id, @RequestParam Long memberId) {
        orderService.deleteOrder(id, memberId);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("주문 삭제 완료"));
    }

}
