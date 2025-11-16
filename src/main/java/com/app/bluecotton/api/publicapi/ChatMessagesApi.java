package com.app.bluecotton.api.publicapi;

import com.app.bluecotton.domain.vo.chat.ChatMessageVO;
import com.app.bluecotton.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chats")
@RequiredArgsConstructor
public class ChatMessagesApi {

    private final ChatMessageService chatMessageService;

    // 채팅방 메시지 불러오기
    @GetMapping("/get-messages/{chatId}")
    public List<ChatMessageVO> getChats(@PathVariable("chatId") Long chatId) {
        ChatMessageVO chatMessageVO = new ChatMessageVO();
        chatMessageVO.setChatId(chatId);
        return chatMessageService.selectAll(chatMessageVO);
    }

    // 읽음 처리
    @PatchMapping("/read/{chatId}/{receiverId}")
    public void markAsRead(
            @PathVariable("chatId") Long chatId,
            @PathVariable("receiverId") Long receiverId
    ) {
        ChatMessageVO chatMessageVO = new ChatMessageVO();
        chatMessageVO.setChatId(chatId);
        chatMessageVO.setChatMessageReceiverId(receiverId);
        chatMessageService.updateReadStatus(chatMessageVO);
    }

    @GetMapping("/{chatId}/messages")
    public List<ChatMessageVO> getMessages(
            @PathVariable Long chatId,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "50") int limit
    ) {
        List<ChatMessageVO> messages = chatMessageService.getMessages(chatId, offset, limit);

        return messages;
    }

}
