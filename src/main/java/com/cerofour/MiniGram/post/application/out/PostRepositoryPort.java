package com.cerofour.MiniGram.post.application.out;

import com.cerofour.MiniGram.post.domain.Post;

public interface PostRepositoryPort {
    Post createPost(Post p);
}
