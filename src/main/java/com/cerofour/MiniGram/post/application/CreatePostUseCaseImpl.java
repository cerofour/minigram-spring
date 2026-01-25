package com.cerofour.MiniGram.post.application;

import com.cerofour.MiniGram.fileIO.application.in.CreateFileUseCase;
import com.cerofour.MiniGram.fileIO.application.out.FileRepositoryPort;
import com.cerofour.MiniGram.post.application.in.CreatePostUseCase;
import com.cerofour.MiniGram.post.application.out.PostRepositoryPort;
import com.cerofour.MiniGram.post.domain.Post;
import com.cerofour.MiniGram.shared.domain.UseCase;
import lombok.RequiredArgsConstructor;

import java.io.InputStream;

@UseCase
@RequiredArgsConstructor
public class CreatePostUseCaseImpl implements CreatePostUseCase {

    private final CreateFileUseCase createFileUseCase;
    private final PostRepositoryPort postRepositoryPort;

    @Override
    public Post createPost(Post p, String filename, InputStream image) {
        Post createdPost = postRepositoryPort.createPost(p);

        createFileUseCase.createPostPicture(createdPost, filename, image);

        return createdPost;
    }
}
