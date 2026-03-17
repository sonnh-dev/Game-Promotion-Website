package com.dxlab.gamepromotionweb;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptTest {

    @Test
    public void testBCrypt() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "123456";

        String hashed = encoder.encode(rawPassword);
        System.out.println("Hashed password: " + hashed);

        boolean matches = encoder.matches(rawPassword, hashed);
        System.out.println("Password matches? " + matches);
    }
}