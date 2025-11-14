package com.app.bluecotton.service;

import com.app.bluecotton.domain.vo.chat.ChatVO;
import com.app.bluecotton.exception.ChatException;
import com.app.bluecotton.repository.ChatDAO;
import com.app.bluecotton.repository.ChatMemberDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatDAO chatDAO;
    private final ChatMemberDAO chatMemberDAO;

    @Override
    public void createChat(ChatVO chatVO) {
        chatDAO.save(chatVO);
    }

    @Override
    public ChatVO selectChatById(Long id) {
        ChatVO chatVO = chatDAO.findById(id).orElseThrow(() -> new ChatException("채팅방이 없습니다."));
        chatVO.setMembers(chatMemberDAO.selectIdByMemberListChatId(id));
        return chatVO;
    }

    @Override
    public List<ChatVO> selectAll() {
        return chatDAO.findAll();
    }

    @Override
    public void delete(Long id) {
        chatDAO.delete(id);
    }

    @Override
    public List<ChatVO> selectChatListByMember(Long memberId) {
        return chatDAO.findByMemberId(memberId);
    }

    @Override
    public Long getChatIdByMemberId(String somTitle, Long memberId){
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("somTitle", somTitle);
        paramMap.put("memberId", memberId);

        return chatDAO.selectIdByTitleWithMemberId(paramMap);
    }

    @Override
    public Long getChatIdByTitle(String chatTitle){
        return chatDAO.selectIdByTitle(chatTitle);
    }

}
