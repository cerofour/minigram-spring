package com.cerofour.MiniGram.post.application;

import com.cerofour.MiniGram.post.application.in.GetPostsUseCase;
import com.cerofour.MiniGram.post.application.out.PostRepositoryPort;
import com.cerofour.MiniGram.post.domain.Post;
import com.cerofour.MiniGram.shared.domain.UseCase;
import com.cerofour.MiniGram.shared.domain.pagination.PageMetadata;
import com.cerofour.MiniGram.shared.domain.pagination.PaginatedResult;
import com.cerofour.MiniGram.user.application.out.UserRepositoryPort;
import com.cerofour.MiniGram.user.domain.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class GetPostsUseCaseImpl implements GetPostsUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PostRepositoryPort postRepositoryPort;

    @Override
    public PaginatedResult<Post> getPostsByUsername(String username, PageMetadata from) {

        Integer userId = userRepositoryPort.findByUsername(username)
                .map(x -> x.getId())
                .orElseThrow(UserNotFoundException::new);

        return postRepositoryPort.getUserPosts(
                userId,
                from
        );
    }
}
