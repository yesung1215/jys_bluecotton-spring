package com.app.bluecotton.api.privateapi;

import com.app.bluecotton.domain.dto.*;
import com.app.bluecotton.service.MemberService;
import com.app.bluecotton.service.MyPageInfoService;
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
@RequestMapping("/private/my-page/*")
public class MyPageInfoApi {

    private final SomService somService;
    private final MemberService memberService;
    private final MyPageInfoService myPageInfoService;

    // 회원정보 출력
    @GetMapping("read-member")
    public ResponseEntity<ApiResponseDTO> readMember(@RequestParam Long id ){
        log.info("회원정보를 불러옵니다");
        log.info("출력: {}", myPageInfoService.selectMemberInfo(id));
        MyPageInfoDTO data =  myPageInfoService.selectMemberInfo(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("회원정보가 출력되었습니다", data));
    }
    // 회원주소 출력
    @GetMapping("member-address")
    public ResponseEntity<ApiResponseDTO<Object>> readMemberAddress(){
        List<String> data = memberService.findAllMemberAddress();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("모든 회원의 주소를 조회했습니다", data));
    }

    // 회원주소 출력
    @PutMapping("update-member")
    public ResponseEntity<ApiResponseDTO<Object>> updateInfo(@RequestBody MyPageInfoDTO myPageInfoDTO){
        String data = "ㅇㅅㅇ";
        myPageInfoService.updateInfo(myPageInfoDTO);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("회원의 정보를 갱신했습니다", data));
    }

}

