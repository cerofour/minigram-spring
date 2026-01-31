package com.cerofour.MiniGram.post.infrastructure.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
public class PostResult {

    UUID postId;
    String description;
    String preSignedURL;
    Instant createdAt;

}
