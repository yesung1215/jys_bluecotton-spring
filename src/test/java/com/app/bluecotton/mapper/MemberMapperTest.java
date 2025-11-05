package com.app.bluecotton.mapper;

import com.app.bluecotton.domain.vo.member.MemberInsertSocialVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class MemberMapperTest {

    @Autowired
    MemberSocialMapper memberSocialMapper;

    @Test
    void insert() {
    }

    @Test
    void insertSocial() {
        MemberInsertSocialVO  memberInsertSocialVO = new MemberInsertSocialVO();

    }

    @Test
    void selectAll() {
    }

    @Test
    void existByMemberEmail() {
    }

    @Test
    void selectIdByEmail() {
    }

    @Test
    void select() {
    }

    @Test
    void selectAllAddress() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}