package com.cerofour.MiniGram.post.application.in;

import com.cerofour.MiniGram.post.domain.Post;
import com.cerofour.MiniGram.shared.domain.pagination.PageMetadata;
import com.cerofour.MiniGram.shared.domain.pagination.PaginatedResult;

public interface GetPostsUseCase {
    PaginatedResult<Post> getPostsByUsername(String username, PageMetadata from);
}
