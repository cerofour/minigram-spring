package com.cerofour.MiniGram.post.domain;

import lombok.Builder;
import lombok.Getter;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
public class Post {
    private UUID id;
    private Integer userId;
    private String description;
    private String pictureKey;
    private LocalDateTime createdAt;
}
