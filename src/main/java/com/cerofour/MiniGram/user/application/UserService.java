package com.cerofour.MiniGram.user.application;

import com.cerofour.MiniGram.auth.application.out.EncryptionPort;
import com.cerofour.MiniGram.shared.domain.UseCase;
import com.cerofour.MiniGram.user.application.in.CreateUserUseCase;
import com.cerofour.MiniGram.user.application.in.FindUserUseCase;
import com.cerofour.MiniGram.user.application.out.UserRepositoryPort;
import com.cerofour.MiniGram.user.domain.User;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class UserService implements CreateUserUseCase, FindUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final EncryptionPort encryptionPort;

    @Override
    public Optional<User> createUser(User u) {

        String encodedPassword = encryptionPort.encrypt(u.getPassword());

        u.setPassword(encodedPassword);
        u.setCreatedAt(LocalDateTime.now());

        return userRepositoryPort.createUser(u);
    }

    @Override
    public List<User> all(Integer page, Integer size) {
        return userRepositoryPort.all(page, size);
    }

    @Override
    public List<User> allWhoseUsernameStartsWith(String substr) {
        return userRepositoryPort.allWhoseUsernameStartsWith(substr);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepositoryPort.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepositoryPort.findByEmail(email);
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepositoryPort.findById(id);
    }
}
