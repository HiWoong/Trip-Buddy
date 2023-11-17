package com.ssafy.enjoytrip.util;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class JWTUtilTest {

    @Autowired
    JWTUtil jwtUtil;

    @Test
    @DisplayName("jwt 검증")
    void jwtValidationTest(){
        String uid = "test";

        String token = jwtUtil.createAccessToken(uid);
        System.out.println("token = " + token);
        Assertions.assertThat(jwtUtil.getUserId(token)).isEqualTo(uid);
    }
}
