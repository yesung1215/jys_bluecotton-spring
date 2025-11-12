package com.app.bluecotton.api.publicapi;

import com.app.bluecotton.domain.dto.*;
import com.app.bluecotton.domain.vo.shop.PaymentStatus;
import com.app.bluecotton.domain.vo.shop.PaymentVO;
import com.app.bluecotton.service.OrderService;
import com.app.bluecotton.service.PaymentService;
import com.app.bluecotton.util.PortOneClient;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sound.sampled.Port;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/payment/*")
public class PaymentApi {

    private final PaymentService paymentService;


    @PostMapping("prepare")
    public ResponseEntity<PortOneResponse> preparePayment(@RequestBody @Valid PaymentPrepareRequest request) {
        log.info("Payment preparation request received: {}", request);
        try {
            // Service 계층의 사전 등록 로직 호출
            PortOneResponse response = paymentService.preparePayment(request);
            // 응답으로 merchantUid를 클라이언트에게 돌려줌
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("Payment preparation failed: {}", e.getMessage(), e);
            // DB 저장 또는 PortOne 사전 등록 실패 시 400 Bad Request 반환
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("verify")
    public ResponseEntity<PortOneDTO> verifyPayment(@RequestBody PaymentVerifyDTO request) {

        Map<String, Object> paymentData = new HashMap<>();

        paymentData.put("imp_uid", request.getImpUid());
        paymentData.put("merchant_uid", request.getMerchantUid());

        try {
            PortOneDTO portOneResponse = paymentService.processPayment(paymentData);
            return ResponseEntity.ok().body(portOneResponse);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

}
