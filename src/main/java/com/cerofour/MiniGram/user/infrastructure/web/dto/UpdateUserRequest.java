package com.cerofour.MiniGram.user.infrastructure.web.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateUserRequest {
    @NotBlank(message = "Fullname cannot be empty")
    @Size(min = 2, max = 100)
    private String fullname;

    @NotNull(message = "Birthdate cannot be null")
    @Past(message = "Birthdate must be in the past")
    private LocalDate birthdate;

    @NotNull
    private Character gender;
}