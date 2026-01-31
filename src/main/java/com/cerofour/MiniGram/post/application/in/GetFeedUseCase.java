package com.cerofour.MiniGram.post.application.in;

import com.cerofour.MiniGram.post.application.dto.PostWithUserDetails;
import com.cerofour.MiniGram.shared.domain.pagination.PageMetadata;
import com.cerofour.MiniGram.shared.domain.pagination.PaginatedResult;
import com.cerofour.MiniGram.user.domain.User;

public interface GetFeedUseCase {
    PaginatedResult<PostWithUserDetails> getFeed(User u, PageMetadata pm);
}
