package com.cerofour.MiniGram.post.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
public class Post {
    private UUID id;
    private Integer userId;
    private String description;

    @Setter
    private String pictureKey;
    private Instant createdAt;

    // Defined counters to make calculations faster
    private Integer likeCount;
    private Integer commentCount;
}
