package com.app.bluecotton.mapper;

import com.app.bluecotton.domain.vo.member.MemberSocialVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberSocialMapper {
    public void insert(MemberSocialVO memberSocialVO);

    public List<String> selectAll(Long id);
}
