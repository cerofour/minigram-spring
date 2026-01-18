package com.cerofour.MiniGram.user.infrastructure.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
public class MyProfileResult {
    private Integer id;
    private String username;
    private String fullname;
    private String email;
    private Character gender;
    private LocalDate birthdate;
    private LocalDateTime createdAt;
    private URL profilePicturePreSignedURL;
}
