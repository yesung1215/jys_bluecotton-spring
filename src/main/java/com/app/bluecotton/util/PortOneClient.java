package com.app.bluecotton.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class PortOneClient {

    @Value("${portone.imp-key}")
    private String impKey;

    @Value("${portone.imp-secret}")
    private String impSecret;

    private final RestTemplate restTemplate; // ✅ 빈 주입

    /** 액세스 토큰 발급 */
    public String getAccessToken() {
        final String url = "https://api.iamport.kr/users/getToken";

        // 헤더: JSON 지정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 바디
        Map<String, Object> payload = new HashMap<>();
        payload.put("imp_key", impKey);
        payload.put("imp_secret", impSecret);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("PortOne access token 발급 실패 (HTTP): " + response.getStatusCode());
        }

        Map<?, ?> body = response.getBody();
        if (body == null) throw new RuntimeException("PortOne access token 발급 실패: 응답 body 없음");

        Object code = body.get("code");
        if (!(code instanceof Number) || ((Number) code).intValue() != 0) {
            throw new RuntimeException("PortOne access token 발급 실패 (API code): " + body.get("message"));
        }

        Map<?, ?> res = castMap(body.get("response"));
        String accessToken = Objects.toString(res.get("access_token"), null);
        if (accessToken == null || accessToken.isBlank()) {
            throw new RuntimeException("PortOne access token 발급 실패: access_token 없음");
        }

        log.info("PortOne access token 발급 성공");
        return accessToken;
    }

    /** imp_uid로 결제 단건 조회 */
    public Map<String, Object> getPaymentByImpUid(String accessToken, String impUid) {
        final String url = "https://api.iamport.kr/payments/" + impUid;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", accessToken); // ✅ Bearer 접두사 없음

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("결제내역 조회 실패 (HTTP): " + response.getStatusCode());
        }

        Map<?, ?> body = response.getBody();
        if (body == null) throw new RuntimeException("결제내역 조회 실패: 응답 body 없음");

        Object code = body.get("code");
        if (!(code instanceof Number) || ((Number) code).intValue() != 0) {
            throw new RuntimeException("PortOne 응답 에러: " + body.get("message"));
        }

        Map<String, Object> payment = castMap(body.get("response"));
        log.info("PortOne 결제내역 조회 성공: imp_uid={}, status={}", impUid, payment.get("status"));
        return payment;
    }

    /** 금액 사전등록 (위변조 방지) */
    public void preparePayment(String accessToken, String merchantUid, long amount) {
        final String url = "https://api.iamport.kr/payments/prepare";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", accessToken); // ✅ 그대로 토큰 값
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("merchant_uid", merchantUid);
        body.put("amount", amount);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("결제 사전등록 실패 (HTTP): " + response.getStatusCode());
        }

        Map<?, ?> respBody = response.getBody();
        if (respBody == null) throw new RuntimeException("결제 사전등록 실패: 응답 body 없음");

        Object code = respBody.get("code");
        if (!(code instanceof Number) || ((Number) code).intValue() != 0) {
            throw new RuntimeException("결제 사전등록 실패 (API code): " + respBody.get("message"));
        }

        log.info("PortOne 결제금액 사전등록 완료: merchant_uid={}, amount={}", merchantUid, amount);
    }

    // ---------- helpers ----------
    @SuppressWarnings("unchecked")
    private static Map<String, Object> castMap(Object obj) {
        if (obj instanceof Map<?, ?> map) {
            return (Map<String, Object>) map;
        }
        throw new IllegalStateException("예상치 못한 응답 구조: " + obj);
    }
}
