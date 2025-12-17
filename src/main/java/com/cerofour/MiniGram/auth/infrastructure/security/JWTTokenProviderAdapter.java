package com.cerofour.MiniGram.auth.infrastructure.security;

import com.cerofour.MiniGram.auth.application.out.TokenProviderPort;
import com.cerofour.MiniGram.auth.domain.AuthenticationResult;
import com.cerofour.MiniGram.configuration.JwtUtil;
import com.cerofour.MiniGram.user.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JWTTokenProviderAdapter implements TokenProviderPort {

    private final JwtUtil jwtUtil;

    @Override
    public AuthenticationResult getToken(User user) {

        String accessToken = jwtUtil.generateToken(user.getUsername());

        return AuthenticationResult.builder()
                .accessToken(accessToken)
                .expiration(jwtUtil.extractClaim(accessToken, Claims::getExpiration).getTime())
                .type("Bearer")
                .build();
    }
}
