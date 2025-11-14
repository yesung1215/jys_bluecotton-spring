package com.app.bluecotton.service;

import com.app.bluecotton.domain.vo.chat.ChatVO;

import java.util.List;

public interface ChatService {
    public void createChat(ChatVO chatVO);
    public ChatVO selectChatById(Long id);
    public List<ChatVO> selectAll();
    public List<ChatVO> selectChatListByMember(Long memberId);
    public void delete(Long id);
    public Long getChatIdByMemberId(String somTitle ,Long memberId);
    public Long getChatIdByTitle(String chatTitle);
}
