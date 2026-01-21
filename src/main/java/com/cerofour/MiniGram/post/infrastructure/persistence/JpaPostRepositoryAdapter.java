package com.cerofour.MiniGram.post.infrastructure.persistence;

import com.cerofour.MiniGram.post.application.out.PostRepositoryPort;
import com.cerofour.MiniGram.post.domain.Post;
import com.cerofour.MiniGram.post.infrastructure.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JpaPostRepositoryAdapter implements PostRepositoryPort {

    private final SpringDataPostRepository postRepository;

    @Override
    public Post createPost(Post p) {

        UUID postUUID = UUID.randomUUID();

        return PostMapper.toDomain(
                postRepository.save(PostEntity.builder()
                        .id(postUUID)
                        .userId(p.getUserId())
                        .pictureLink(String.format("posts/%s", postUUID.toString()))
                        .description(p.getDescription())
                .build()));
    }
}
