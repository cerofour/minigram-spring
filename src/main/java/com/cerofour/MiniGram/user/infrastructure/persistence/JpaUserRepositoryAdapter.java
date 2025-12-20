package com.cerofour.MiniGram.user.infrastructure.persistence;

import com.cerofour.MiniGram.shared.infrastructure.DateUtils;
import com.cerofour.MiniGram.user.application.out.UserRepositoryPort;
import com.cerofour.MiniGram.user.domain.User;
import com.cerofour.MiniGram.user.domain.exception.UsernameInvalidException;
import com.cerofour.MiniGram.user.infrastructure.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaUserRepositoryAdapter implements UserRepositoryPort {
    private final SpringDataUserRepository userRepository;

    @Override
    public Optional<User> createUser(User u) {

        Optional<User> foundByUsername = findByUsername(u.getUsername());
        Optional<User> foundByEmail = findByEmail(u.getEmail());

        if (foundByUsername.isPresent()) {
            throw new UsernameInvalidException(String.format("Username '%s' already exists.", u.getUsername()));
        }

        if (foundByEmail.isPresent()) {
            throw new UsernameInvalidException(String.format("Email '%s' already registered.", u.getEmail()));
        }

        UserEntity userEntity = userRepository.save(UserMapper.toEntity(u));

        return Optional.of(UserMapper.toDomain(userEntity));
    }

    // TODO: Maybe this should be an interface abstract method ???
    public User save(UserEntity u) {
        return UserMapper.toDomain(userRepository.save(u));
    }

    @Override
    public List<User> all(Integer page, Integer size) {
        return List.of();
    }

    @Override
    public List<User> allWhoseUsernameStartsWith(String substr) {
        return List.of();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public User updateUser(User u) {

        userRepository.updateBasicInfo(
                u.getId(),
                u.getFullname(),
                Integer.valueOf(u.getGender()),
                u.getBirthdate()
        );

        return u;
    }
}
