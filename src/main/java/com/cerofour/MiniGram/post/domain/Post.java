package com.cerofour.MiniGram.post.domain;

import com.cerofour.MiniGram.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
public class Post {
    private UUID id;
    private User user;
    private String description;

    @Setter
    private String pictureKey;
    private LocalDateTime createdAt;
}
