package com.app.bluecotton.domain.vo.chat;

import lombok.Data;

@Data
public class ChatUserVO {
    private Long id;
    private boolean chatRoomUserStatus;
    private Long userId;
    private Long chatRoomId;
}
