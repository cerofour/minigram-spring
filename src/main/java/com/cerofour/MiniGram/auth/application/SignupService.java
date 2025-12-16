package com.cerofour.MiniGram.auth.application;

import com.cerofour.MiniGram.auth.application.in.SignUpUseCase;
import com.cerofour.MiniGram.auth.domain.SignUpCommand;
import com.cerofour.MiniGram.notification.application.NotificationPort;
import com.cerofour.MiniGram.shared.domain.UseCase;
import com.cerofour.MiniGram.user.application.in.CreateUserUseCase;
import com.cerofour.MiniGram.user.domain.User;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class SignupService implements SignUpUseCase {
    private final CreateUserUseCase createUserUseCase;
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
        notificationPort.sendNotification("Se ha registrado una nueva cuenta.");
    }
}
