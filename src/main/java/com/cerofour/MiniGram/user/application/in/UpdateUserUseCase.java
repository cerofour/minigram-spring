package com.cerofour.MiniGram.user.application.in;

import com.cerofour.MiniGram.user.domain.User;

import java.io.InputStream;
import java.time.LocalDate;

public interface UpdateUserUseCase {
    User updateUserProfilePicture(String username, String filename, InputStream profilePictureIs);

    User updateUserData(String username, String fullname, Character gender, LocalDate birthdate);
}
