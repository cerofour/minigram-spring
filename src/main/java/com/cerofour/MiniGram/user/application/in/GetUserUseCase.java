package com.cerofour.MiniGram.user.application.in;

import com.cerofour.MiniGram.user.domain.User;
import com.cerofour.MiniGram.user.domain.UserProfile;

import java.net.URL;
import java.util.List;
import java.util.Optional;

public interface GetUserUseCase {
    List<User> all(Integer page, Integer size);
    List<User> allWhoseUsernameStartsWith(String substr);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Integer id);

    // Socials use cases

    UserProfile getUserProfile(String username);

    URL getUserProfilePicturePreSignedURL(User user);
}
