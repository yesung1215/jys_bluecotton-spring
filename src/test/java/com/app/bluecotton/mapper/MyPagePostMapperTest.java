package com.app.bluecotton.mapper;

import com.app.bluecotton.domain.dto.MyPagePostWriteDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class MyPagePostMapperTest {

    @Autowired
    MyPagePostMapper myPagePostMapper;

    @Test
    void readPostWrite() {
        myPagePostMapper.readPostWrite(1L);
    }

    @Test
    void readPostLike() {
        myPagePostMapper.readPostLike(1L);
    }
}