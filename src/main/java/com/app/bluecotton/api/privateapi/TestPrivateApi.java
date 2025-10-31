package com.app.bluecotton.api.privateapi;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test-private/*")
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
