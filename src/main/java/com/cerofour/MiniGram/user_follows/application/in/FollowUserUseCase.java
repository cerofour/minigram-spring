package com.cerofour.MiniGram.user_follows.application.in;

import com.cerofour.MiniGram.user.domain.User;

public interface FollowUserUseCase {
    void follow(String currentUser, String targetUser);

    void unfollow(String currentUser, String targetUser);
}
