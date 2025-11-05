package com.app.bluecotton.repository;

import com.app.bluecotton.domain.vo.member.MemberSocialVO;
import com.app.bluecotton.mapper.MemberSocialMapper;
import com.app.bluecotton.mapper.MyTestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberSocialDAO {
    private final MemberSocialMapper memberSocialMapper;

    //  소셜 정보 추가
    public void save(MemberSocialVO memberSocialVO){
        memberSocialMapper.insert(memberSocialVO);
    }
    //  소셜 프로바이더 조회
    public List<String> findSocialProviderById(Long id){
        return memberSocialMapper.selectAll(id);
    }
}
