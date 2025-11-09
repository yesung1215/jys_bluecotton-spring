package com.app.bluecotton.api.mypage;

import com.app.bluecotton.domain.dto.ApiResponseDTO;
import com.app.bluecotton.domain.dto.MemberResponseDTO;
import com.app.bluecotton.domain.dto.SomResponseDTO;
import com.app.bluecotton.domain.vo.member.MemberVO;
import com.app.bluecotton.domain.vo.som.SomVO;
import com.app.bluecotton.service.MemberService;
import com.app.bluecotton.service.SomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/my-page/*")
public class MyPageApi {

    private final SomService somService;
    private final MemberService memberService;

    // 솜 정보 출력
    @GetMapping("read-som")
    public ResponseEntity<ApiResponseDTO> readSom() {
        log.info("솜 정보를 불러옵니다");
        log.info("출력: {}", somService.findAllSom());
        List<SomVO> data = somService.findAllSom();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("솜 전체를 조회했습니다", data));
    }

    // 회원정보 출력
    @GetMapping("read-member")
    public ResponseEntity<ApiResponseDTO> readMember(@RequestParam Long id ){
        log.info("회원정보를 불러옵니다");
        log.info("출력: {}", memberService.getMemberById(id));
        MemberResponseDTO data =  memberService.getMemberById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("회원정보가 출력되었습니다", data));
    }
    // 회원주소 출력
    @GetMapping("member-address")
    public ResponseEntity<ApiResponseDTO<Object>> readMemberAddress(){
        List<String> data = memberService.findAllMemberAddress();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("모든 회원의 주소를 조회했습니다", data));
    }
}
