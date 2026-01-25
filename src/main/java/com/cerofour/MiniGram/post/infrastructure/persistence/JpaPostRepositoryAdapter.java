package com.cerofour.MiniGram.post.infrastructure.persistence;

import com.cerofour.MiniGram.post.application.out.PostRepositoryPort;
import com.cerofour.MiniGram.post.domain.Post;
import com.cerofour.MiniGram.post.infrastructure.PostMapper;
import com.cerofour.MiniGram.shared.domain.pagination.PageMetadata;
import com.cerofour.MiniGram.shared.domain.pagination.PaginatedResult;
import com.cerofour.MiniGram.shared.infrastructure.PaginationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    @Override
    public PaginatedResult<Post> getUserPosts(Integer userId, PageMetadata p) {
        Page<Post> posts = postRepository.findByUserId(userId, PaginationMapper.toPageable(p))
                .map(PostMapper::toDomain);

        PaginatedResult<Post> result = new PaginatedResult<Post>(posts.getContent(), PaginationMapper.from(posts));

        return result;
    }
}
