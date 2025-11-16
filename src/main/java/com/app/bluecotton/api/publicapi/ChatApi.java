package com.app.bluecotton.api.publicapi;

import com.app.bluecotton.domain.dto.ApiResponseDTO;
import com.app.bluecotton.domain.dto.ChatCreateDTO;
import com.app.bluecotton.domain.vo.chat.ChatMemberVO;
import com.app.bluecotton.domain.vo.chat.ChatVO;
import com.app.bluecotton.service.ChatMemberService;
import com.app.bluecotton.service.ChatService;
import com.app.bluecotton.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/chat/*")
@RequiredArgsConstructor
public class ChatApi {

    private final ChatService chatService;
    private final ChatMemberService chatMemberService;
    private final MemberService memberService;

    // 채팅방 목록 가져오기
    @GetMapping("/get-rooms")
    public ResponseEntity<ApiResponseDTO> getAllRooms() {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("채팅방 목록 조회 성공", chatService.selectAll()));
    }

    @GetMapping("/get-join-rooms/{id}")
    public ResponseEntity<ApiResponseDTO> getJoinRooms(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("채팅방 목록 조회 성공", chatService.selectChatListByMember(id)));
    }

    @GetMapping("/get-rooms/{id}")
    public ChatVO getRoom(@PathVariable Long id) {
        return chatService.selectChatById(id);
    }

    @PostMapping("/create-rooms")
    public ResponseEntity<ApiResponseDTO> createRoom(@RequestBody ChatCreateDTO chatCreateDTO) {
        ChatVO createChatVO = new ChatVO(chatCreateDTO);
        Map<String, Object> data = new HashMap();

        // 방 개설
        chatService.createChat(createChatVO);
        // 방 개설 후 아이디 추가 (방장)
        Long newChatId = createChatVO.getId();

        ChatMemberVO chatMemberVO = new ChatMemberVO();
        chatMemberVO.setChatMemberRole("OWNER");
        chatMemberVO.setChatMemberStatus("ACTIVE");
        chatMemberVO.setMemberId(chatCreateDTO.getMemberId());
        chatMemberVO.setChatId(newChatId);
        chatMemberService.createChatMember(chatMemberVO);

        data.put("newChatId", newChatId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("채팅방 개설 성공", data));
    }

    @DeleteMapping("/remove-rooms/{id}")
    public void deleteRoom(@PathVariable Long id) {
        chatService.delete(id);
    }

    @GetMapping("/join-room")
    public ResponseEntity<ApiResponseDTO> joinRoom(@RequestParam String somTitle, @RequestParam String memberEmail) {

        ChatMemberVO chatMemberVO = new ChatMemberVO();
        chatMemberVO.setChatMemberRole("OWNER");
        chatMemberVO.setChatMemberStatus("ACTIVE");
        chatMemberVO.setMemberId(memberService.getMemberIdByMemberEmail(memberEmail));
        chatMemberVO.setChatId(chatService.getChatIdByTitle(somTitle));
        Integer existingCount = chatMemberService.exists(chatMemberVO);
        if (existingCount == 0) {
            chatMemberService.createChatMember(chatMemberVO);
        }
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("채팅방 개설 성공", chatMemberVO));
    }
}