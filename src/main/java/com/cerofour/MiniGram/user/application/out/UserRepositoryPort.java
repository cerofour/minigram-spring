package com.cerofour.MiniGram.user.application.out;

import com.cerofour.MiniGram.user.domain.User;
import com.cerofour.MiniGram.user.domain.UserProfile;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {
    Optional<User>  createUser(User u);
    List<User>      all(Integer page, Integer size);
    List<User>      allWhoseUsernameStartsWith(String substr);
    Optional<User>  findByUsername(String username);
    Optional<User>  findByEmail(String email);
    Optional<User>  findById(Integer id);

    UserProfile     getUserProfile(User u);
    User            updateUser(User u);
}
