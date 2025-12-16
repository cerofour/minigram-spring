package com.cerofour.MiniGram.user.infrastructure;

import com.cerofour.MiniGram.user.domain.User;
import com.cerofour.MiniGram.user.infrastructure.persistence.UserEntity;

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
}
