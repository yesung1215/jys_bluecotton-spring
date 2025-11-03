package com.app.bluecotton.domain.vo.chat;

import lombok.Data;

import java.util.Date;

@Data
public class ChatVO {
    private Long id;
    private String chatTitle;
    private Date chatCreateAt;
    private boolean chatStatus;
    private Integer ChatMemberCount;
}
