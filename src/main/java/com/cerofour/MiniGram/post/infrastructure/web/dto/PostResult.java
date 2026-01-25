package com.cerofour.MiniGram.post.infrastructure.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
public class PostResult {

    UUID postId;
    String username;
    String description;
    String preSignedURL;
    LocalDateTime createdAt;

}
