package com.cerofour.MiniGram.post.application;

import com.cerofour.MiniGram.fileIO.application.in.GetFileUseCase;
import com.cerofour.MiniGram.fileIO.application.out.FileRepositoryPort;
import com.cerofour.MiniGram.post.application.in.GetPostsUseCase;
import com.cerofour.MiniGram.post.application.out.PostRepositoryPort;
import com.cerofour.MiniGram.post.domain.Post;
import com.cerofour.MiniGram.shared.domain.UseCase;
import com.cerofour.MiniGram.shared.domain.pagination.PageMetadata;
import com.cerofour.MiniGram.shared.domain.pagination.PaginatedResult;
import com.cerofour.MiniGram.user.application.out.UserRepositoryPort;
import com.cerofour.MiniGram.user.domain.User;
import com.cerofour.MiniGram.user.domain.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class GetPostsUseCaseImpl implements GetPostsUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PostRepositoryPort postRepositoryPort;

    private final GetFileUseCase getFileUseCase;

    @Override
    public PaginatedResult<Post> getPostsByUsername(String username, PageMetadata from) {

        User u = userRepositoryPort.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        Integer userId = u.getId();

        PaginatedResult<Post> result = postRepositoryPort.getUserPosts(
                userId,
                from
        );

        // convert the key stored in the database to actual urls from the cloud provider
        result.data().forEach(
                        x -> x.setPictureKey(
                                getFileUseCase.getPostPicture(x).toString())
                        );

        return result;
    }
}
