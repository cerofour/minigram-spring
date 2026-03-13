package com.cerofour.MiniGram.auth.infrastructure.web;

import com.cerofour.MiniGram.auth.application.in.SignInUseCase;
import com.cerofour.MiniGram.auth.application.in.SignUpUseCase;
import com.cerofour.MiniGram.auth.domain.AuthenticationResult;
import com.cerofour.MiniGram.auth.domain.SignInCommand;
import com.cerofour.MiniGram.auth.domain.SignUpCommand;
import com.cerofour.MiniGram.auth.infrastructure.web.dto.SignInUserRequest;
import com.cerofour.MiniGram.auth.infrastructure.web.dto.SignupUserRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final SignUpUseCase signUpUseCase;
    private final SignInUseCase signInUseCase;

    @PostMapping("/signUp")
    public ResponseEntity<Void> signUp(@RequestBody @Valid SignupUserRequest request) {

        signUpUseCase.signUp(
                new SignUpCommand(
                        request.getUsername(),
                        request.getEmail(),
                        request.getFullname(),
                        request.getPassword(),
                        request.getGender().charAt(0),
                        request.getBirthdate()
                )
        );

        return ResponseEntity
                .status(201)
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResult> signIn(@RequestBody @Valid SignInUserRequest request) {

        return ResponseEntity.ok(signInUseCase.signIn(
                new SignInCommand(request.getUsername(), request.getPassword())));
    }
}
