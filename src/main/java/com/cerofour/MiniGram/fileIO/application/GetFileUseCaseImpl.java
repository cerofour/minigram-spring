package com.cerofour.MiniGram.fileIO.application;

import com.cerofour.MiniGram.fileIO.application.in.GetFileUseCase;
import com.cerofour.MiniGram.fileIO.application.out.FileRepositoryPort;
import com.cerofour.MiniGram.post.domain.Post;
import com.cerofour.MiniGram.shared.domain.UseCase;
import lombok.RequiredArgsConstructor;

import java.net.URL;
import java.time.Duration;

@UseCase
@RequiredArgsConstructor
public class GetFileUseCaseImpl implements GetFileUseCase {

    private final FileRepositoryPort fileRepositoryPort;

    @Override
    public URL getProfilePicture(Integer userId) {
        return fileRepositoryPort.getSignedURL(
                String.format("%s/%s", "profile_pictures", userId.toString()),
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
