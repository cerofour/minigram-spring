package com.cerofour.MiniGram.auth.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthenticationResult {
    private String accessToken;
    private Long expiration;
    private String type;
}