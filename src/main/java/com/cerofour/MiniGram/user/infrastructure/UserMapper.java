package com.cerofour.MiniGram.user.infrastructure;

import com.cerofour.MiniGram.user.domain.User;
import com.cerofour.MiniGram.user.infrastructure.persistence.UserEntity;
import com.cerofour.MiniGram.user.infrastructure.web.dto.MyProfileResult;
import com.cerofour.MiniGram.user.infrastructure.web.dto.UserProfileResult;
import com.cerofour.MiniGram.user.infrastructure.web.dto.UserResult;

import java.net.URL;

public class UserMapper {
    static public User toDomain(UserEntity x) {
        return User.builder()
                .id(x.getId())
                .username(x.getUsername())
                .fullname(x.getFullname())
                .email(x.getEmail())
                .birthdate(x.getBirthdate())
                .password(x.getPassword())
                .gender(x.getGender().shortValue())
                .build();
    }

    static public UserEntity toEntity(User x) {
        return UserEntity.builder()
                .id(x.getId())
                .username(x.getUsername())
                .fullname(x.getFullname())
                .email(x.getEmail())
                .birthdate(x.getBirthdate())
                .password(x.getPassword())
                .gender(Integer.valueOf(x.getGender()))
                .build();
    }

    static public UserResult toWebResult(User u) {
        return UserResult.builder()
                .id(u.getId())
                .fullname(u.getFullname())
                .username(u.getUsername())
                .email(u.getEmail())
                .birthdate(u.getBirthdate())
                .gender(u.getGender().equals((short) 1) ? 'M' : 'F')
                .createdAt(u.getCreatedAt())
                .build();
    }

    static public MyProfileResult toMyProfileResult(User u, URL profilePictureUrl) {
        return MyProfileResult.builder()
                .id(u.getId())
                .fullname(u.getFullname())
                .username(u.getUsername())
                .email(u.getEmail())
                .birthdate(u.getBirthdate())
                .gender(u.getGender().equals((short) 1) ? 'M' : 'F')
                .profilePicturePreSignedURL(profilePictureUrl)
                .createdAt(u.getCreatedAt())
                .build();

    }

    static public UserProfileResult toUserProfileResult(User u, URL profilePictureUrl) {
        return UserProfileResult.builder()
                .fullname(u.getFullname())
                .username(u.getUsername())
                .birthdate(u.getBirthdate())
                .gender(u.getGender().equals((short) 1) ? 'M' : 'F')
                .profilePicturePreSignedURL(profilePictureUrl)
                .build();
    }
}
