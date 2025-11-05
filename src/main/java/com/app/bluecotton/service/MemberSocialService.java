package com.app.bluecotton.service;

import com.app.bluecotton.domain.vo.member.MemberSocialVO;

import java.util.List;

public interface MemberSocialService {
    public void registerMemberSocial(MemberSocialVO memberSocialVO);
    public List<String> findAllProvidersById(Long id);
}
