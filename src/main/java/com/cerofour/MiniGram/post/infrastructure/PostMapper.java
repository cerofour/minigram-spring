package com.cerofour.MiniGram.post.infrastructure;

import com.cerofour.MiniGram.post.domain.Post;
import com.cerofour.MiniGram.post.infrastructure.persistence.PostEntity;
import com.cerofour.MiniGram.post.infrastructure.web.dto.PostResult;

import java.sql.Timestamp;

public class PostMapper {
    public static Post toDomain(PostEntity save) {

        return Post.builder()
                .id(save.getId())
                .userId(save.getUserId())
                .description(save.getDescription())
                .pictureKey(String.format("posts/%s", save.getId().toString()))
                .createdAt(save.getCreatedAt().toLocalDateTime())
                .build();
    }

    public static PostResult toResult(Post p) {
        return PostResult.builder()
                .postId(p.getId())
                .createdAt(p.getCreatedAt())
                .description(p.getDescription())
                .preSignedURL(p.getPictureKey())
                .build();
    }
}
