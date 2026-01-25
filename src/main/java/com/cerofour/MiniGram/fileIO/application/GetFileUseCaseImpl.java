package com.cerofour.MiniGram.fileIO.application;

import com.cerofour.MiniGram.fileIO.application.in.GetFileUseCase;
import com.cerofour.MiniGram.fileIO.application.out.FileRepositoryPort;
import com.cerofour.MiniGram.post.domain.Post;
import com.cerofour.MiniGram.shared.domain.UseCase;
import com.cerofour.MiniGram.user.domain.User;
import lombok.RequiredArgsConstructor;

import java.net.URL;
import java.time.Duration;

@UseCase
@RequiredArgsConstructor
public class GetFileUseCaseImpl implements GetFileUseCase {

    private final FileRepositoryPort fileRepositoryPort;

    @Override
    public URL getProfilePicture(User user) {
        return fileRepositoryPort.getSignedURL(
                String.format("%s/%d", "profile_pictures", user.getId()),
                Duration.ofMinutes(30)
        );
    }

    @Override
    public URL getPostPicture(Post post) {
        return fileRepositoryPort.getSignedURL(
                String.format("posts/%s", post.getId().toString()),
                Duration.ofMinutes(30)
        );
    }
}
