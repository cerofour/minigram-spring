package com.cerofour.MiniGram.auth.application;

import com.cerofour.MiniGram.auth.application.in.SignInUseCase;
import com.cerofour.MiniGram.auth.application.in.SignUpUseCase;
import com.cerofour.MiniGram.auth.application.out.EncryptionPort;
import com.cerofour.MiniGram.auth.application.out.TokenProviderPort;
import com.cerofour.MiniGram.auth.domain.AuthenticationResult;
import com.cerofour.MiniGram.auth.domain.SignInCommand;
import com.cerofour.MiniGram.auth.domain.SignUpCommand;
import com.cerofour.MiniGram.auth.domain.exception.BadCredentialsException;
import com.cerofour.MiniGram.notification.application.NotificationPort;
import com.cerofour.MiniGram.shared.domain.UseCase;
import com.cerofour.MiniGram.user.application.in.CreateUserUseCase;
import com.cerofour.MiniGram.user.application.in.FindUserUseCase;
import com.cerofour.MiniGram.user.domain.User;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class SignupService implements SignUpUseCase, SignInUseCase {
    private final CreateUserUseCase createUserUseCase;
    private final FindUserUseCase findUserUseCase;
    private final EncryptionPort encryptionPort;
    private final TokenProviderPort tokenProviderPort;
    private final NotificationPort notificationPort;

    @Override
    public void signUp(SignUpCommand command) {
        User user = User.builder()
                .username(command.username())
                .fullname(command.fullname())
                .email(command.email())
                .birthdate(command.birthdate())
                .password(command.password())
                .gender((short) ((command.gender() == 'M') ? 1 : 0))
                .build();

        createUserUseCase.createUser(user);
        notificationPort.suscribeToEmailService(command.email());
        notificationPort.sendNotification(command.email(), "Se ha registrado una nueva cuenta.");
    }

    @Override
    public AuthenticationResult signIn(SignInCommand command) {

        return findUserUseCase.findByUsername(command.username())
                .map((user) -> {

                    if (encryptionPort.equals(command.password(), user.getPassword())) {

                        notificationPort.sendNotification(user.getEmail(), "Se ha registrado un nuevo inicio de sesión en tu cuenta.");

                        return tokenProviderPort.getToken(user);
                    }

                    throw new BadCredentialsException("Wrong password, try again");
                })
                .orElseThrow(() -> new
                        BadCredentialsException(
                            String.format("Account with username '%s' doesn't exists.", command.username())));
    }
}
