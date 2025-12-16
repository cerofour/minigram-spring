package com.cerofour.MiniGram.auth.domain;

import java.time.LocalDate;

public record SignUpCommand(
        String username, String email, String fullname, String password, Character gender, LocalDate birthdate
) {
}
