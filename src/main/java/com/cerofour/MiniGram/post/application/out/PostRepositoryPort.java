package com.cerofour.MiniGram.post.application.out;

import com.cerofour.MiniGram.post.domain.Post;
import com.cerofour.MiniGram.shared.domain.pagination.PageMetadata;
import com.cerofour.MiniGram.shared.domain.pagination.PaginatedResult;

public interface PostRepositoryPort {
    Post createPost(Post p);
    PaginatedResult<Post> getUserPosts(Integer userId, PageMetadata p);
}
