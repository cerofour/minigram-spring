package com.cerofour.MiniGram.user.application;

import com.cerofour.MiniGram.auth.application.out.EncryptionPort;
import com.cerofour.MiniGram.fileIO.application.out.FileRepositoryPort;
import com.cerofour.MiniGram.shared.domain.UseCase;
import com.cerofour.MiniGram.shared.infrastructure.DateUtils;
import com.cerofour.MiniGram.user.application.in.CreateUserUseCase;
import com.cerofour.MiniGram.user.application.in.FindUserUseCase;
import com.cerofour.MiniGram.user.application.in.UpdateUserUseCase;
import com.cerofour.MiniGram.user.application.out.UserRepositoryPort;
import com.cerofour.MiniGram.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class UserService implements CreateUserUseCase, FindUserUseCase, UpdateUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final EncryptionPort encryptionPort;
    private final FileRepositoryPort fileRepositoryPort;

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

    @Override
    public User updateUserProfilePicture(String username, String filename, InputStream profilePictureIs) {
        User u = userRepositoryPort.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("'%s' username not found.", username)));

        fileRepositoryPort.upload("minigram-s3-bucket",
                String.format("%s/%d","profile_pictures", u.getId()), filename, profilePictureIs);

        return u;
    }

    @Override
    public User updateUserData(String username, String fullname, Character gender, LocalDate birthdate) {
        User u = userRepositoryPort.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("'%s' username not found.", username)));

        if (fullname != null && !fullname.isEmpty())
            u.setFullname(fullname);

        if (gender != null)
            u.setGender((short)(gender.equals('M') ? 1 : 0));

        if (birthdate != null && DateUtils.isOlderThan18(birthdate)) {
            u.setBirthdate(birthdate);
        }

        return userRepositoryPort.updateUser(u);
    }
}
