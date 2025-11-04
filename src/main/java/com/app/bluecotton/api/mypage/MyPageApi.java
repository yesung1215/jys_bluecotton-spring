package com.app.bluecotton.api.mypage;

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
@RequestMapping("/mypage/*")
public class MyPageApi {
    private final MemberService memberService;

    // 회원정보 출력
    @GetMapping("read-member")
    public ResponseEntity<ApiResponseDTO<Object>> readMember(@RequestParam Long id ){
        log.info("회원정보를 불러옵니다");
        log.info("출력: {}", memberService.getMemberById(id));
        memberService.getMemberById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("회원정보가 출력되었습니다"));
    }

    @GetMapping("member-address")
    public ResponseEntity<ApiResponseDTO<Object>> memberAddress(){
        List<String> data = memberService.findALlMemberAddress();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("모든 회원의 주소를 조회했습니다", data));
    }
}
