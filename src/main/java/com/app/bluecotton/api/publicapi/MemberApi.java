package com.app.bluecotton.api.publicapi;

import com.app.bluecotton.domain.dto.ApiResponseDTO;
import com.app.bluecotton.domain.dto.MemberResponseDTO;
import com.app.bluecotton.domain.vo.member.MemberProfileVO;
import com.app.bluecotton.domain.vo.member.MemberVO;
import com.app.bluecotton.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member/*")
public class MemberApi {

    private final MemberService memberService;

    //  스웨거 설명서 추가해야함
    //  회원 가입
    @PostMapping("register")
    public ResponseEntity<ApiResponseDTO<Object>> register(@RequestBody MemberVO  memberVO) {
        memberService.register(memberVO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.of("회원가입이 완료되었습니다")); // 201
    }

    //  회원 수정
    @PutMapping("modify")
    public ResponseEntity<ApiResponseDTO<Object>> modify(@RequestBody MemberVO  memberVO) {
        memberService.modify(memberVO);
        return  ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("정보 수정이 완료되었습니다"));
    }

    //  회원 탈퇴
    @DeleteMapping("unregister")
    public ResponseEntity<ApiResponseDTO<Object>> unregister(@RequestBody Long  id) {
        memberService.withdraw(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponseDTO.of("회원 탈퇴가 완료되었습니다"));
    }

    //  회원 정보 상세 조회 테스트
    @GetMapping("read")
    public ResponseEntity<ApiResponseDTO> read(@RequestParam Long memberId) {
        MemberResponseDTO data = memberService.getMemberById(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("회원 정보를 불러왔습니다",data));
    }

    @GetMapping("profile")
    public ResponseEntity<ApiResponseDTO> getProfile(@RequestParam Long memberId) {
        MemberProfileVO memberProfileVO = memberService.getMemberProfileImage(memberId);

        Map<String,Object> result = new HashMap<>();
        result.put("memberProfilePath",memberProfileVO.getMemberProfilePath());
        result.put("memberProfileName",memberProfileVO.getMemberProfileName());

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("이미지를 불러왔습니다", result));
    }
}
