package com.cerofour.MiniGram.fileIO.application.in;

import com.cerofour.MiniGram.post.domain.Post;
import com.cerofour.MiniGram.user.domain.User;

import java.net.URL;

public interface GetFileUseCase {

    URL getProfilePicture(User user);

    URL getPostPicture(Post post);

}
