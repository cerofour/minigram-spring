package com.cerofour.MiniGram.post.application;

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

    private final FileRepositoryPort fileRepositoryPort;
    private final PostRepositoryPort postRepositoryPort;

    @Override
    public Post createPost(Post p, String filename, InputStream image) {
        Post createdPost = postRepositoryPort.createPost(p);

        // always make sure this id is the same as the one stored in the db for this post
        // this is critical because the uuid will be used as key of the post in S3
        String postIdAsStr = createdPost.getId().toString();

        // TODO: REFACTOR THESE
        fileRepositoryPort.upload(
                "minigram-s3-bucket",
                String.format("posts/%s", postIdAsStr),
                filename,
                image
        );

        return createdPost;
    }
}
