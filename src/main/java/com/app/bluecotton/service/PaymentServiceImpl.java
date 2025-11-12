package com.app.bluecotton.service;

import com.app.bluecotton.domain.dto.PaymentPrepareRequest;
import com.app.bluecotton.domain.dto.PortOneDTO;
import com.app.bluecotton.domain.dto.PortOneResponse;
import com.app.bluecotton.domain.vo.shop.PaymentStatus;
import com.app.bluecotton.domain.vo.shop.PaymentVO;
import com.app.bluecotton.repository.OrderDAO;
import com.app.bluecotton.repository.PaymentDAO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentDAO paymentDAO;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final OrderDAO orderDAO;

    private static final String PORTONE_API_BASE_URL = "https://api.iamport.kr";
    private static final String GET_TOKEN_URL = PORTONE_API_BASE_URL + "/users/getToken";
    private static final String GET_PAYMENT_INFO_URL = PORTONE_API_BASE_URL + "/payments/{imp_uid}";
    private static final String PREPARE_PAYMENT_URL = PORTONE_API_BASE_URL + "/payments/prepare";

    @Value("${portone.api-url}")
    private String apiUrl;

    @Value("${portone.imp-key}")
    private String impKey;

    @Value("${portone.imp-secret}")
    private String impSecret;

    private String getAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("imp_key", impKey);
        requestBody.put("imp_secret", impSecret);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(GET_TOKEN_URL, HttpMethod.POST, request, String.class);

            if(response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                JsonNode root = new ObjectMapper().readTree(response.getBody());
                if(root.get("code").asInt() == 0) {
                    return root.get("response").get("access_token").asText();
                }
            }
            throw new RuntimeException("PortOne Access Token 발급 실패: " + response.getBody());
        } catch (Exception e) {
            log.error("토큰 발급 중 예외 발생", e);
            throw new RuntimeException("PortOne API 통신 오류(토큰 발급)" , e);
        }
    }

//    @Override
//    public PortOneDTO processPayment(PortOneResponse portOneResponse) {
//        return null;
//    }

    private String generateMerchantUid(Long orderId) {
        return "ORD_" + orderId + "_" + UUID.randomUUID().toString().substring(0,8).toUpperCase();
    }
    @Override
    public PortOneDTO processPayment(Map<String, Object> paymentData) {
        String impUid = (String) paymentData.get("imp_uid");
        String merchantUid = (String) paymentData.get("merchant_uid");

        if (impUid == null || impUid.isEmpty() || merchantUid == null || merchantUid.isEmpty()) {
            log.error("필수 결제 데이터 누락: impUid={}, merchantUid={}", impUid, merchantUid);
            throw new IllegalArgumentException("결제 검증에 필요한 필수 데이터(imp_uid 또는 merchant_uid)가 누락되었습니다.");
        }

        String accessToken = getAccessToken();

        PortOneDTO portOnePaymentInfo = getPaymentInfoFromPortOne(accessToken, impUid);

        if (portOnePaymentInfo == null || portOnePaymentInfo.getResponse() == null) {
            throw new RuntimeException("PortOne에서 유효한 결제 응답 정보를 받지 못했습니다.");
        }

        verifyPayment(merchantUid, portOnePaymentInfo);

        Long paidAmount = portOnePaymentInfo.getResponse().getAmount();

        int rowsUpdated = paymentDAO.markSuccessByMerchantUid(merchantUid, impUid, paidAmount, PaymentStatus.COMPLETED);

        if(rowsUpdated == 0) {
            log.info("DB업데이트 실패 ");
            throw new RuntimeException("결제 정보 DB 반영에 실패했습니다.");
        }

        log.info("결제 최종 처리 완료 : merchantUid={}", merchantUid);

        return portOnePaymentInfo;

    }

    @Override
    public PortOneResponse preparePayment(PaymentPrepareRequest request) {
        Long memberId = request.getMemberId();
        if(memberId == null) {
            throw new IllegalArgumentException("회원 정보가 없습니다");
        }


        Long orderId = request.getOrderId() != null ? request.getOrderId() : 1L;

        String merchantUid = generateMerchantUid(orderId);

        Long finalAmount = request.getAmount();

        if(finalAmount == null || finalAmount <= 0 ) {
            throw new IllegalArgumentException("결제할 금액이 유효하지 앖습니다.");
        }
        PaymentVO paymentVO = new PaymentVO();
        paymentVO.setPaymentPrice(finalAmount.intValue());
        paymentVO.setPaymentType(request.getPaymentType());
        paymentVO.setPaymentStatus(PaymentStatus.PENDING); // 초기 상태는 PENDING
        paymentVO.setOrderId(orderId);
        paymentVO.setMemberId(memberId);
        paymentVO.setMerchantUid(merchantUid);

        paymentDAO.save(paymentVO);

        String accessToken = getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("merchant_uid", merchantUid);
        requestBody.put("amount", request.getAmount());

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            // 5. PortOne 사전 등록 API 호출
            ResponseEntity<String> response = restTemplate.exchange(
                    PREPARE_PAYMENT_URL,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            if (response.getStatusCode() != HttpStatus.OK) {
                log.error("PortOne 사전 등록 API 응답 오류: {}", response.getBody());
                throw new RuntimeException("PortOne 사전 등록 실패");
            }

            // 6. 클라이언트에게 merchantUid 반환 (결제창 호출에 사용)
            return PortOneResponse.builder()
                    .merchantUid(merchantUid)
                    .build();

        } catch (Exception e) {
            log.error("결제 사전 등록 중 통신 오류 또는 예외 발생: merchantUid={}", merchantUid, e);
            // 트랜잭션이 @Transactional에 의해 롤백되도록 RuntimeException을 다시 던짐
            throw new RuntimeException("결제 사전 등록 처리 중 오류 발생", e);
        }
    }

    private PortOneDTO getPaymentInfoFromPortOne(String accessToken, String impUid) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(headers);

        try {

            Map<String, String> uriVariables = Collections.singletonMap("imp_uid", impUid);

            ResponseEntity<PortOneDTO> response = restTemplate.exchange(GET_PAYMENT_INFO_URL, HttpMethod.GET, request, PortOneDTO.class, uriVariables);

            PortOneDTO result = response.getBody();
            if(result == null || result.getCode() != 0) {
                throw new RuntimeException("PortOne 결제 정보 조회 실패: " + (result != null ? result.getMessage() : "응답없음"));
            }
            return result;
        } catch (Exception e) {
            log.error("결제 정보 조회 중 에러 발생", e);
            throw new RuntimeException("PortOne API 통신 오류(결제 조회)", e);
        }
    }

    private void verifyPayment(String merchantUid, PortOneDTO portOnePaymentInfo) {

        Long dbAmount = paymentDAO.findExpectedAmountByMerchantUid(merchantUid);

        if(dbAmount == null || dbAmount.longValue() <= 0) {
            throw  new RuntimeException("DB에서 주문번호에 대한 예상 금액 못 찾음");
        }

        Long actualPaidAmount = portOnePaymentInfo.getResponse().getAmount();
        String paidStatus = portOnePaymentInfo.getResponse().getStatus();

        if(!"paid".equals(paidStatus)) {
            throw new RuntimeException("결제 상태가 'paid'가 아닙니다 : " + paidStatus);
        }



        log.info("결제 검증 성공 : merchantUid={}, amount={}", merchantUid, actualPaidAmount);
    }
}
