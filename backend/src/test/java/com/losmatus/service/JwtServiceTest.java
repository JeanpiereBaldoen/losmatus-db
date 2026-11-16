package com.losmatus.service;

import com.losmatus.config.CustomUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secretKey", "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970");
        ReflectionTestUtils.setField(jwtService, "jwtExpirationMs", 3600000L);
    }

    @Test
    void testGenerateAndValidateToken() {
        CustomUserDetails userDetails = new CustomUserDetails(
                10L,
                "cliente@losmatus.com",
                "encodedPassword",
                true,
                "CLIENTE",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_CLIENTE"))
        );

        String token = jwtService.generateToken(userDetails, 10L, "CLIENTE");

        assertNotNull(token);
        assertEquals("cliente@losmatus.com", jwtService.extractUsername(token));
        assertEquals(10L, jwtService.extractIdUsuario(token));
        assertEquals("CLIENTE", jwtService.extractRol(token));
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    void testTokenInvalidForDifferentUser() {
        CustomUserDetails user1 = new CustomUserDetails(
                1L, "user1@losmatus.com", "pass", true, "ADMIN",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );
        CustomUserDetails user2 = new CustomUserDetails(
                2L, "user2@losmatus.com", "pass", true, "CLIENTE",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_CLIENTE"))
        );

        String token = jwtService.generateToken(user1, 1L, "ADMIN");

        assertFalse(jwtService.isTokenValid(token, user2));
    }
}

