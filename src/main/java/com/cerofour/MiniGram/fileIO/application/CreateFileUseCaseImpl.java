package com.cerofour.MiniGram.fileIO.application;

import com.cerofour.MiniGram.fileIO.application.in.CreateFileUseCase;
import com.cerofour.MiniGram.fileIO.application.out.FileRepositoryPort;
import com.cerofour.MiniGram.post.application.FileKeyGenerator;
import com.cerofour.MiniGram.post.domain.Post;
import com.cerofour.MiniGram.shared.domain.UseCase;
import com.cerofour.MiniGram.user.domain.User;
import lombok.RequiredArgsConstructor;

import java.io.InputStream;

@UseCase
@RequiredArgsConstructor
public class CreateFileUseCaseImpl implements CreateFileUseCase {

    private final FileRepositoryPort fileRepositoryPort;

    @Override
    public void createProfilePicture(User user, String filename, InputStream is) {
        fileRepositoryPort.upload(
                FileKeyGenerator.getProfilePictureKey(user),
                filename,
                is
        );
    }

    @Override
    public void createPostPicture(Post post, String filename, InputStream is) {
        fileRepositoryPort.upload(
                FileKeyGenerator.getPostPictureKey(post),
                filename,
                is
        );
    }
}
