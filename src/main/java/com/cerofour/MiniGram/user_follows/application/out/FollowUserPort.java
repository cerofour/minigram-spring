package com.cerofour.MiniGram.user_follows.application.out;

import com.cerofour.MiniGram.user.domain.User;

public interface FollowUserPort {

    boolean exists(String currentUser, String targetUser);

    void follow(String currentUser, String targetUser);
    void unfollow(String currentUser, String targetUser);
}
