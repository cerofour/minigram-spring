package com.cerofour.MiniGram.like.application;

import com.cerofour.MiniGram.like.application.in.ToggleLikeUseCase;
import com.cerofour.MiniGram.like.application.out.LikeRepositoryPort;
import com.cerofour.MiniGram.like.domain.LikeResult;
import com.cerofour.MiniGram.shared.domain.UseCase;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class LikePostUseCaseImpl implements ToggleLikeUseCase {

    private final LikeRepositoryPort likeRepositoryPort;

    @Override
    public LikeResult likePost(UUID postId, Integer userId) {

        if (likeRepositoryPort.exists(postId, userId)) {
            likeRepositoryPort.delete(postId, userId);
            return LikeResult.UNLIKED;
        }

        likeRepositoryPort.save(postId, userId);
        return LikeResult.LIKED;
    }
}
