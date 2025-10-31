package com.app.bluecotton.repository;

import com.app.bluecotton.mapper.MyTestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MyTestDAO {

    private final MyTestMapper myTestMapper;

    public void save(){
        myTestMapper.myTest();
    }
}
