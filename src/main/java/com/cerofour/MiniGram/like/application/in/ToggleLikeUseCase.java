package com.cerofour.MiniGram.like.application.in;

import com.cerofour.MiniGram.like.domain.LikeResult;

import java.util.UUID;

public interface ToggleLikeUseCase {
    LikeResult likePost(UUID postId, Integer userId);
}
