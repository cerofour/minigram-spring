package com.cerofour.MiniGram.post.application.in;

import com.cerofour.MiniGram.post.domain.Post;

import java.io.InputStream;

public interface CreatePostUseCase {

    Post createPost(Post p, String filename, InputStream image);

}
