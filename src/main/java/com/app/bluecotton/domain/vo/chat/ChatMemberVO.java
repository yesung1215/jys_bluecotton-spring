package com.app.bluecotton.domain.vo.chat;

import lombok.Data;

@Data
public class ChatMemberVO {
    private Long id;
    private Long memberId;
    private Long chatId;
}