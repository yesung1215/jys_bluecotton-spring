package com.app.bluecotton.api.publicapi;

import com.app.bluecotton.domain.dto.ApiResponseDTO;
import com.app.bluecotton.domain.vo.member.MemberVO;
import com.app.bluecotton.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member/*")
public class MemberApi {
    private final MemberService memberService;

    @PostMapping("register")
    public ResponseEntity<ApiResponseDTO<Object>> register(@RequestBody MemberVO memberVO){
        memberService.register(memberVO);
        // 버그버그버그
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.of("회원가입이 완료되었습니다"));
    }

    @GetMapping("member-address")
    public ResponseEntity<ApiResponseDTO<Object>> memberAddress(){
        List<String> data = memberService.findALlMemberAddress();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("모든 회원의 주소를 조회했습니다", data));
    }
}
