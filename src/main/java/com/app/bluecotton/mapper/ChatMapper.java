package com.app.bluecotton.mapper;

import com.app.bluecotton.domain.vo.chat.ChatVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface ChatMapper {
    public void insertChat(ChatVO chatVO);
    public Optional<ChatVO> selectChatById(Long id);
    public List<ChatVO> selectAll();
    public void delete(Long id);
    public List<ChatVO> selectChatListByMember(Long memberId);
    public Long selectIdByTitleWithMemberId(Map<String, Object> paramMap);
    public Long selectIdByTitle(String chatTitle);
}
