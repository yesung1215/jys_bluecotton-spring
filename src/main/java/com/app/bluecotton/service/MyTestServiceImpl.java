package com.app.bluecotton.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class MyTestServiceImpl implements MyTestService {

    @Override
    public void myTest() {

    }

}
