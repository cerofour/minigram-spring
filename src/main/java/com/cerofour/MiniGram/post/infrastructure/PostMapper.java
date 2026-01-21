package com.cerofour.MiniGram.post.infrastructure;

import com.cerofour.MiniGram.post.domain.Post;
import com.cerofour.MiniGram.post.infrastructure.persistence.PostEntity;

public class PostMapper {
    public static Post toDomain(PostEntity save) {

        return Post.builder()
                .id(save.getId())
                .userId(save.getUserId())
                .description(save.getDescription())
                .pictureKey(String.format("posts/%s", save.getId().toString()))
                //.createdAt(save.getCreatedAt().toLocalDateTime())
                .build();

    }
}
