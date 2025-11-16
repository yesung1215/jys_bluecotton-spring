package com.app.bluecotton.api.publicapi.websocket;

import com.app.bluecotton.domain.vo.chat.ChatMemberVO;
import com.app.bluecotton.domain.vo.chat.ChatMessageVO;
import com.app.bluecotton.service.ChatMemberService;
import com.app.bluecotton.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatMessageApi {

    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatMemberService chatMemberService;

    @MessageMapping("/chat/send")
    public void sendMessage(ChatMessageVO chatMessageVO) {

        // 1) JOIN/LEAVE도 모두 메시지 테이블에 저장
        chatMessageService.insert(chatMessageVO);

        // 2) ChatMember 테이블 동기화 (회원 상태 관리)
        if ("JOIN".equals(chatMessageVO.getChatMessageType())) {
            chatMemberService.createChatMember(new ChatMemberVO(chatMessageVO));
        } else if ("LEAVE".equals(chatMessageVO.getChatMessageType())) {
            chatMemberService.delete(new ChatMemberVO(chatMessageVO));
        }

        // 3) 브로드캐스트 (모든 메시지 전송)
        simpMessagingTemplate.convertAndSend(
                "/sub/chat/room/" + chatMessageVO.getChatId(),
                chatMessageVO
        );
    }
}
