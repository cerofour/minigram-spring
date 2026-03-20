package com.cerofour.MiniGram.post.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class PostWithUserDetails {
    private UUID id;

    @Setter
    private String pictureLink;

    private String description;

    // user
    private Integer userId;
    private String username;
    @Setter
    private String userProfilePictureLink;

    private Instant createdAt;

    // Counters
    private Long likeCount;
    private Integer commentCount;

    private Boolean liked;
}
