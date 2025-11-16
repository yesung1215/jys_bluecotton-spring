package com.app.bluecotton.service;

import com.app.bluecotton.domain.dto.ChatMemberResponseDTO;
import com.app.bluecotton.domain.vo.chat.ChatMemberVO;
import com.app.bluecotton.repository.ChatMemberDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMemberServiceImpl implements ChatMemberService {

    private final ChatMemberDAO chatMemberDAO;

    @Override
    public void createChatMember(ChatMemberVO chatMemberVO) {
        if (chatMemberDAO.exists(chatMemberVO) == 0) {
            chatMemberDAO.insertChatMember(chatMemberVO);
        }
        // 이미 참여 중인 경우는 조용히 넘어감 (중복 참여 방지)
    }

    @Override
    public void delete(ChatMemberVO chatMemberVO) {
        chatMemberDAO.delete(chatMemberVO);
    }

    @Override
    public Integer exists(ChatMemberVO chatMemberVO) {
        return chatMemberDAO.exists(chatMemberVO);
    }

    @Override
    public List<ChatMemberResponseDTO> selectIdByMemberListChatId(Long chatId){
        return chatMemberDAO.selectIdByMemberListChatId(chatId);
    }
}
