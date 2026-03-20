package com.cerofour.MiniGram.fileIO.application.in;

import com.cerofour.MiniGram.post.domain.Post;

import java.net.URL;

public interface GetFileUseCase {

    URL getProfilePicture(Integer userId);

    URL getPostPicture(Post post);

}
