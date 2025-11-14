package com.app.bluecotton.repository;

import com.app.bluecotton.domain.vo.chat.ChatVO;
import com.app.bluecotton.mapper.ChatMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ChatDAO {

    private final ChatMapper chatMapper;

    public void save(ChatVO chatVO) {
        chatMapper.insertChat(chatVO);
    }

    public Optional<ChatVO> findById(Long id) {
        return chatMapper.selectChatById(id);
    }

    public List<ChatVO> findAll() {
        return chatMapper.selectAll();
    }

    public List<ChatVO> findByMemberId(Long memberId){
        return chatMapper.selectChatListByMember(memberId);
    }

    public Long selectIdByTitleWithMemberId(Map<String, Object> paramMap){
        return chatMapper.selectIdByTitleWithMemberId(paramMap);
    }

    public Long selectIdByTitle(String chatTitle){
        return chatMapper.selectIdByTitle(chatTitle);
    }

    public void delete(Long id){
        chatMapper.delete(id);
    }
}
