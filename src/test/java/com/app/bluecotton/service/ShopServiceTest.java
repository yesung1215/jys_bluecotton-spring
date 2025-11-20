package com.app.bluecotton.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

@SpringBootTest
@Slf4j
public class ShopServiceTest {

    @Autowired
    private ShopService shopService;

    private Random random = new Random();

    @Test
    void generateProductLikeDummyData() {

        for(long productId = 1L; productId <= 110L; productId++) {

            int likeCount = random.nextInt(50);

            for(int i = 0; i < likeCount; i++) {
                long memberId = 1 + random.nextInt(50);
                shopService.toggleLike(memberId, productId);
            }

        }
    }
}
