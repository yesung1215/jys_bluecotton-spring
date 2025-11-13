package com.app.bluecotton.api.publicapi;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat-bot")
public class ChatBotApi {

    @Value("${openai.api-key}")
    private String openaiApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping
    public ResponseEntity<Map<String, Object>> chat(@RequestBody Map<String, String> request) {
        String message = request.get("message");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openaiApiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-4o-mini");
        body.put("messages", List.of(
                Map.of("role", "system", "content", """
                    너는 Blue Cotton 사이트의 전용 챗봇이야.
                    사용자에게 솜, 블루코튼샵, 오늘의 솜, 주변 솜에 대해서 친절히 설명해줘.
                    주요 기능:
                    - "솜"은 챌린지를 Blue Cotton만의 용어로 바꾼 것이야. 즉, 솜 = 챌린지.
                    - "홈"에서는 전체 솜 목록을 한눈에 볼 수 있어.
                    - "주변 솜"은 사용자 위치 주변에서 진행 중인 솜들을 지도에서 볼 수 있어.
                    - "오늘의 솜"은 진행 중인 솜에 대한 일기나 블로그 형태의 후기 작성 페이지야.
                    - "블루코튼 샵"은 솜 마스코트를 이용한 굿즈를 판매하는 곳이야.
                    - 솜 등록은 플로팅 버튼으로 할 수 있고, 후기 작성은 "작성하기" 버튼으로 진행돼.
                    - 블루코튼 샵에서는 현금 또는 내부 화폐인 "캔디"로 결제할 수 있어.
                    - 솜 완료 시 캔디를 지급받으며 누적 시 랭크가 상승하고, 랭크가 높을수록 캔디 보상이 증가해.
                    - 솜 진행 중에는 인증 절차를 꼭 거쳐야 해.
                    답변할 때는 항상 친근하고, 사이트 맥락 안에서 대화해.
                """),
                Map.of("role", "user", "content", message)
        ));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            Map response = restTemplate.postForObject(
                    "https://api.openai.com/v1/chat/completions",
                    entity,
                    Map.class
            );
            System.out.println("OpenAI API 응답: " + response);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "OpenAI API 요청 실패");
            errorResponse.put("details", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }

    }
}
