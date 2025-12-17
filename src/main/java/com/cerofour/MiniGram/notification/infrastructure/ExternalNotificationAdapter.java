package com.cerofour.MiniGram.notification.infrastructure;

import com.cerofour.MiniGram.notification.application.NotificationPort;
import com.cerofour.MiniGram.shared.domain.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

@UseCase
@RequiredArgsConstructor
public class ExternalNotificationAdapter implements NotificationPort {

    private final MailSender mailSender;

    @Override
    public void suscribeToEmailService(String email) {
        System.out.printf("Subscribing %s to notification service.", email);
    }

    @Override
    public void sendNotification(String email, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("llacsahuanga.buques@gmail.com");
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("test subject");
        simpleMailMessage.setText(message);
        this.mailSender.send(simpleMailMessage);
    }
}
