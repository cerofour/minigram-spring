package com.cerofour.MiniGram.user.infrastructure.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.net.URL;

@Builder
@Getter
public class UserProfilePictureResult {
    private URL preSignedUrl;
}
