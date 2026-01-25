package com.cerofour.MiniGram.fileIO.application.in;

import com.cerofour.MiniGram.post.domain.Post;
import com.cerofour.MiniGram.user.domain.User;

import java.io.InputStream;

public interface CreateFileUseCase {

    void createProfilePicture(User user, String filename, InputStream is);

    void createPostPicture(Post post, String filename, InputStream is);

}
