package com.cerofour.MiniGram.post.application;

import com.cerofour.MiniGram.post.domain.Post;
import com.cerofour.MiniGram.user.domain.User;

public class FileKeyGenerator {

    public static String getProfilePictureKey(User u) {
        return String.format("profile_pictures/%d", u.getId());
    }

    public static String getPostPictureKey(Post p) {
        return String.format("posts/%s", p.getId().toString());
    }

}
