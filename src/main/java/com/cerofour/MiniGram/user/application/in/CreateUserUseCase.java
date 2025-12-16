package com.cerofour.MiniGram.user.application.in;

import com.cerofour.MiniGram.user.domain.User;

import java.util.Optional;

public interface CreateUserUseCase {
    Optional<User> createUser(User u);
}
