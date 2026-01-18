package com.cerofour.MiniGram.user_follows.application;

import com.cerofour.MiniGram.shared.domain.UseCase;
import com.cerofour.MiniGram.user.application.in.GetUserUseCase;
import com.cerofour.MiniGram.user.application.out.UserRepositoryPort;
import com.cerofour.MiniGram.user.domain.exception.UsernameInvalidException;
import com.cerofour.MiniGram.user_follows.application.in.FollowUserUseCase;
import com.cerofour.MiniGram.user.domain.User;
import com.cerofour.MiniGram.user_follows.application.out.FollowUserPort;
import com.cerofour.MiniGram.user_follows.domain.exception.InvalidFollowException;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class FollowUserUseCaseImpl implements FollowUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final FollowUserPort followUserPort;

    @Override
    public void follow(String currentUser, String targetUser) {
        if (currentUser.equals(targetUser)) {
            throw new InvalidFollowException("User can't follow itself.");
        }

        if (followUserPort.exists(currentUser, targetUser)) {
            throw new InvalidFollowException(String.format("<%s> already follows <%s>", currentUser, targetUser));
        }

        followUserPort.follow(currentUser, targetUser);
    }

    @Override
    public void unfollow(String currentUser, String targetUser) {
        if (currentUser.equals(targetUser)) {
            throw new InvalidFollowException("User cannot unfollow itself.");
        }

        if (!followUserPort.exists(currentUser, targetUser)) {
            throw new InvalidFollowException(String.format("<%s> doesn't follow <%s>", currentUser, targetUser));
        }

        followUserPort.unfollow(currentUser, targetUser);

    }
}
