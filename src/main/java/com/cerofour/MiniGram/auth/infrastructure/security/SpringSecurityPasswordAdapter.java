package com.cerofour.MiniGram.auth.infrastructure.security;

import com.cerofour.MiniGram.auth.application.out.EncryptionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpringSecurityPasswordAdapter implements EncryptionPort {

    private final PasswordEncoder springPasswordEncoder;

    @Override
    public String encrypt(String plain) {
        return springPasswordEncoder.encode(plain);
    }

    @Override
    public Boolean equals(String plain, String encrypted) {
        return springPasswordEncoder.matches(plain, encrypted);
    }
}
