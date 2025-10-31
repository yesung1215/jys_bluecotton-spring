package com.app.bluecotton.domain.vo.chat;

import lombok.Data;

import java.util.Date;

@Data
public class ChatRoomContentVO {
    private Long id;
    private String chatContent;
    private Date chatRoomDate;
    private Long chatRoomId;
}
