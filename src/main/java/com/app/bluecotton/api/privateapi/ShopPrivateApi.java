package com.app.bluecotton.api.privateapi;

import com.app.bluecotton.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/private/shop/*")
@RequiredArgsConstructor
@Slf4j
public class ShopPrivateApi {

    private final ShopService shopService;


}
