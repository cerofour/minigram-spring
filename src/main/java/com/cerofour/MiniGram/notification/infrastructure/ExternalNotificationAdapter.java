package com.cerofour.MiniGram.notification.infrastructure;

import com.cerofour.MiniGram.notification.application.NotificationPort;
import com.cerofour.MiniGram.shared.domain.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

@UseCase
@RequiredArgsConstructor
public class ExternalNotificationAdapter implements NotificationPort {

    @Override
    public void suscribeToEmailService(String email) {
        System.out.printf("Subscribing %s to notification service.", email);
    }

    @Override
    public void sendNotification(String email, String message) {
        System.out.printf(
                """
                        MAILTO <%s>:\s
                        
                        %s""", email, message);
    }
}
