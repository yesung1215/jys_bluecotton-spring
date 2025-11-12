package com.app.bluecotton.api.publicapi;


import com.app.bluecotton.domain.dto.ApiResponseDTO;
import com.app.bluecotton.domain.dto.OrderCartDTO;
import com.app.bluecotton.domain.dto.OrderCheckoutDTO;
import com.app.bluecotton.domain.dto.OrderDTO;
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
    public ResponseEntity<ApiResponseDTO<Optional<OrderVO>>>  selectOrderById(@RequestParam Long id, @RequestParam Long memberId) {
        Optional<OrderVO> order = orderService.selectOrderById(id, memberId);
        return ResponseEntity.ok(ApiResponseDTO.of("단일주문 확인 완료", order));
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
