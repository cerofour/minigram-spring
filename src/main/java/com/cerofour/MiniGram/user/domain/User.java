package com.cerofour.MiniGram.user.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Builder
@Getter
@Setter
public class User {
    private Integer id;
    private String username;
    private String email;
    private String fullname;
    private Short gender;
    private LocalDate birthdate;
    private String password;
    private LocalDateTime createdAt;
}
