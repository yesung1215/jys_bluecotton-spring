package com.app.bluecotton.api.publicapi;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test-public/*")
public class TestPublicApi {

    @GetMapping("/test")
    public void testPublicGetApi() {};

    @PostMapping("/test")
    public void testPublicPostApi() {};

    @PutMapping("/test")
    public void testPublicPutApi() {};

    @DeleteMapping("/test")
    public void testPublicDeleteApi() {};
}
