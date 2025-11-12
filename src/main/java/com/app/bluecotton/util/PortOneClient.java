package com.app.bluecotton.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class PortOneClient {

    @Value("${portone.api-url}")
    private String baseUrl;

    @Value("${portone.imp-key}")
    private String impKey;

    @Value("${portone.imp-secret}")
    private String impSecret;

    private final RestTemplate restTemplate;


    private static void setAuth(HttpHeaders headers, String accessToken) {
        // 환경에 따라 "Bearer " 접두사가 없어도 되지만, 최신 스펙 권장 형식으로 맞춥니다.
        headers.set("Authorization", "Bearer " + accessToken);
    }

    private static String enc(String v) {
        return URLEncoder.encode(v, StandardCharsets.UTF_8);
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> castMap(Object obj) {
        if (obj instanceof Map<?, ?> map) return (Map<String, Object>) map;
        throw new IllegalStateException("예상치 못한 응답 구조: " + obj);
    }

    @PostConstruct
    void checkKeys() {
        log.info("PortOne impKey length={}", impKey == null ? 0 : impKey.length());
        log.info("PortOne impSecret length={}", impSecret == null ? 0 : impSecret.length());
    }


    public String getAccessToken() {
        String url = "https://api.iamport.kr/users/getToken";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> payload = Map.of("imp_key", impKey, "imp_secret", impSecret);

        try {
            ResponseEntity<Map> response =
                    restTemplate.postForEntity(url, new HttpEntity<>(payload, headers), Map.class);

            Map<?, ?> body = response.getBody();
            if (body == null) throw new RuntimeException("PortOne getToken: body null");

            Object code = body.get("code");
            if (!(code instanceof Number) || ((Number) code).intValue() != 0) {
                throw new RuntimeException("PortOne getToken code!=0: " + body.get("message"));
            }

            Map<?, ?> res = castMap(body.get("response"));
            String accessToken = Objects.toString(res.get("access_token"), null);
            if (accessToken == null || accessToken.isBlank()) {
                throw new RuntimeException("PortOne getToken: access_token empty");
            }

            log.info("PortOne access token 발급 성공");
            return accessToken;

        } catch (RestClientResponseException e) {
            log.error("PortOne getToken 실패 status={} body={}",
                    e.getRawStatusCode(), e.getResponseBodyAsString());
            throw new RuntimeException("PortOne getToken 실패: " + e.getRawStatusCode(), e);
        }
    }


    public Map<String, Object> getPaymentByImpUid(String accessToken, String impUid) {
        final String url = baseUrl + "/payments/" + enc(impUid);

        HttpHeaders headers = new HttpHeaders();
        setAuth(headers, accessToken);

        ResponseEntity<Map> response =
                restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), Map.class);

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


    public Long getPreparedAmount(String accessToken, String merchantUid) {
        final String url = baseUrl + "/payments/prepare?merchant_uid=" + URLEncoder.encode(merchantUid, StandardCharsets.UTF_8);

        HttpHeaders headers = new HttpHeaders();
        setAuth(headers, accessToken);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        ResponseEntity<Map> res = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), Map.class);
        if (res.getStatusCode() != HttpStatus.OK) {
            log.warn("사전등록 조회 실패 (HTTP) merchant_uid={}, status={}", merchantUid, res.getStatusCode());
            return null;
        }

        Map<?, ?> body = res.getBody();
        if (body == null) return null;

        Object code = body.get("code");
        if (!(code instanceof Number) || ((Number) code).intValue() != 0) {
            log.warn("사전등록 조회 실패 (API) merchant_uid={}, code={}, msg={}", merchantUid, code, body.get("message"));
            return null;
        }

        Object resp = body.get("response");
        if (resp instanceof Map<?, ?> respMap) {
            Object amt = respMap.get("amount");
            if (amt instanceof Number) {
                return ((Number) amt).longValue();
            }
        }
        return null;
    }
    public void preparePayment(String accessToken, String merchantUid, long amount) {
        final String url = baseUrl + "/payments/prepare";

        HttpHeaders headers = new HttpHeaders();
        setAuth(headers, accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON)); // 선택

        Map<String, Object> body = new HashMap<>();
        body.put("merchant_uid", merchantUid);
        body.put("amount", amount);

        try {
            ResponseEntity<Map> response =
                    restTemplate.postForEntity(url, new HttpEntity<>(body, headers), Map.class);

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("결제 사전등록 실패 (HTTP): " + response.getStatusCode());
            }

            Map<?, ?> respBody = response.getBody();
            if (respBody == null) throw new RuntimeException("결제 사전등록 실패: 응답 body 없음");

            Object code = respBody.get("code");
            if (!(code instanceof Number)) {
                throw new RuntimeException("결제 사전등록 실패: code 형식 오류");
            }

            int codeInt = ((Number) code).intValue();
            if (codeInt != 0) {
                String msg = Objects.toString(respBody.get("message"), "");
                // 이미 등록된 merchant_uid인 케이스 멱등 체크
                if (msg.contains("이미 등록된 merchant_uid")) {
                    Long prepared = getPreparedAmount(accessToken, merchantUid);
                    if (prepared != null && prepared.longValue() == amount) {
                        log.info("PortOne 사전등록 멱등 통과 (이미 등록, 금액 동일): merchant_uid={}, amount={}", merchantUid, amount);
                        return;
                    }
                }
                throw new RuntimeException("결제 사전등록 실패 (API code): " + msg);
            }

            // code==0인 경우 response 내부도 점검(선택)
            Object respData = respBody.get("response");
            if (respData instanceof Map<?, ?> respMap) {
                Object amt = respMap.get("amount");
                if (amt instanceof Number && ((Number) amt).longValue() != amount) {
                    log.warn("사전등록 응답 금액이 요청과 다릅니다. req={}, resp={}", amount, amt);
                }
            }

            log.info("PortOne 결제금액 사전등록 완료: merchant_uid={}, amount={}", merchantUid, amount);

        } catch (RestClientResponseException e) {
            // 4xx/5xx더라도 '이미 등록'이면 멱등 통과 시도
            String bodyStr = e.getResponseBodyAsString();
            if (bodyStr != null && bodyStr.contains("이미 등록된 merchant_uid")) {
                Long prepared = getPreparedAmount(accessToken, merchantUid);
                if (prepared != null && prepared.longValue() == amount) {
                    log.info("PortOne 사전등록 멱등 통과 (HTTP 예외지만 금액 동일): merchant_uid={}, amount={}", merchantUid, amount);
                    return;
                }
            }
            log.error("PortOne 사전등록 실패 status={} body={}", e.getRawStatusCode(), bodyStr);
            throw new RuntimeException("결제 사전등록 실패: " + e.getRawStatusCode(), e);
        }
    }


    public Map<String, Object> getPaymentByMerchantUid(String accessToken, String merchantUid) {
        final String url = baseUrl + "/payments/find/" + enc(merchantUid);

        HttpHeaders headers = new HttpHeaders();
        setAuth(headers, accessToken);

        ResponseEntity<Map> response =
                restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), Map.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("find by merchant_uid 실패 (HTTP): " + response.getStatusCode());
        }
        Map<?, ?> body = response.getBody();
        if (body == null) throw new RuntimeException("find by merchant_uid 실패: 응답 body 없음");

        Object code = body.get("code");
        if (!(code instanceof Number) || ((Number) code).intValue() != 0) {
            throw new RuntimeException("find by merchant_uid 실패: " + body.get("message"));
        }

        Map<String, Object> payment = castMap(body.get("response"));
        log.info("PortOne find by merchant_uid 성공: merchant_uid={}, status={}" , merchantUid, payment.get("status"));
        return payment;
    }
}
