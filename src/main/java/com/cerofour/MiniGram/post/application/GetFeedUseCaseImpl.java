package com.cerofour.MiniGram.post.application;

import com.cerofour.MiniGram.fileIO.application.in.GetFileUseCase;
import com.cerofour.MiniGram.post.application.dto.PostWithUserDetails;
import com.cerofour.MiniGram.post.application.in.GetFeedUseCase;
import com.cerofour.MiniGram.post.application.out.PostRepositoryPort;
import com.cerofour.MiniGram.post.domain.Post;
import com.cerofour.MiniGram.shared.domain.UseCase;
import com.cerofour.MiniGram.shared.domain.pagination.PageMetadata;
import com.cerofour.MiniGram.shared.domain.pagination.PaginatedResult;
import com.cerofour.MiniGram.user.domain.User;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class GetFeedUseCaseImpl implements GetFeedUseCase {

    private final PostRepositoryPort postRepositoryPort;
    private final GetFileUseCase getFileUseCase;

    @Override
    public PaginatedResult<PostWithUserDetails> getFeed(User u, PageMetadata pm) {

        PaginatedResult<PostWithUserDetails> feed = postRepositoryPort.getFeed(u.getId(), pm);

        feed.data().forEach(
                x -> {
                    x.setPictureLink(
                            getFileUseCase.getPostPicture(
                                            Post.builder()
                                                    .id(x.getId())
                                                    .build())
                                    .toString());
                    x.setUserProfilePictureLink(getFileUseCase.getProfilePicture(x.getUserId()).toString());
                }
        );

        return feed;
    }
}
