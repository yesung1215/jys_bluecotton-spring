package com.app.bluecotton.service;

import com.app.bluecotton.domain.dto.TokenDTO;
import com.app.bluecotton.domain.vo.member.MemberVO;

import java.util.Map;

public interface AuthService {
    //  로그인 -> 성공 시 토큰 반환(Access, Refresh)
    public Map<String, String> login(MemberVO memberVO);

    //  Redis에 Refresh 토큰을 저장
    public boolean saveRefreshToken(TokenDTO tokenDTO);

    //  Redis에 저장된 Refresh 토큰이 유효한지 확인
    public boolean validateRefreshToken(TokenDTO tokenDTO);

    //  Refresh으로 Access을 재발급
    public String reissueAccessToken(TokenDTO tokenDTO);

    //  Redis에 등록된 Refresh을 무효화
    public boolean revokeRefreshToken(TokenDTO tokenDTO);

    //  Refresh을 블랙리스트에 추가
    public boolean saveBlacklistedToken(TokenDTO tokenDTO);

    //  Refresh가 블랙리스트인지 아닌지 확인
    public boolean isBlacklistedRefreshToken(TokenDTO tokenDTO);
}
