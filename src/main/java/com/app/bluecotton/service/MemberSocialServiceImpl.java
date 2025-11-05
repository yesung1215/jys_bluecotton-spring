package com.app.bluecotton.service;

import com.app.bluecotton.domain.vo.member.MemberSocialVO;
import com.app.bluecotton.repository.MemberSocialDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class MemberSocialServiceImpl implements MemberSocialService {

    private final MemberSocialDAO memberSocialDAO;

    @Override
    public void registerMemberSocial(MemberSocialVO memberSocialVO) {
        memberSocialDAO.save(memberSocialVO);
    }

    @Override
    public List<String> findAllProvidersById(Long id) {
        return memberSocialDAO.findSocialProviderById(id);
    }
}
