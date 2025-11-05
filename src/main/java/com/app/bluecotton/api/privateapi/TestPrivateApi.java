package com.app.bluecotton.api.privateapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/private/test-private/*")
@Slf4j
public class TestPrivateApi {

    @GetMapping("/test")
    public void testPrivateGetApi() {};

    @PostMapping("/test")
    public void testPrivatePostApi() {};

    @PutMapping("/test")
    public void testPrivatePutApi() {};

    @DeleteMapping("/test")
    public void testPrivateDeleteApi() {};
}
