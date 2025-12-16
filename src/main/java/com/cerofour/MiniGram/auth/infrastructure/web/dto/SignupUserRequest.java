package com.cerofour.MiniGram.auth.infrastructure.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class SignupUserRequest {

    @NotBlank(message = "Email cannot be empty")
    @Email
    String email;

    @NotBlank(message = "username cannot be empty")
    String username;

    @NotBlank(message = "Password cannot be empty.")
    @Size(min = 8, max = 32, message = "Password must be a string of 8 to 32 characters in length.")
    String password;

    @NotBlank(message = "Fullname cannot be empty")
    String fullname;

    @NotNull(message = "Birthdate cannot be null")
    @Past(message = "Birthdate must be in the past")
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate birthdate;

    @NotBlank(message = "Gender cannot be empty")
    @Size(min = 1, max = 1)
    String gender;
}
