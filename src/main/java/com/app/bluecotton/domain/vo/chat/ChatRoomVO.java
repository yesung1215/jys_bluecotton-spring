package com.app.bluecotton.domain.vo.chat;

import lombok.Data;

import java.util.Date;

@Data
public class ChatRoomVO {
    private Long id;
    private String chatRoomTitle;
    private Date chatRoomDate;
}
