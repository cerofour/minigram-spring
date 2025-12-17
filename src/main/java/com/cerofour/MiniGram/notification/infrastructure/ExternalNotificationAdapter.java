package com.cerofour.MiniGram.notification.infrastructure;

import com.cerofour.MiniGram.notification.application.NotificationPort;
import com.cerofour.MiniGram.shared.domain.UseCase;

@UseCase
public class ExternalNotificationAdapter implements NotificationPort {

    @Override
    public void suscribeToEmailService(String email) {
        System.out.printf("Subscribing %s to notification service.", email);
    }

    @Override
    public void sendNotification(String email, String message) {
        System.out.printf("Sending message: '%s' to '%s'", message, email);
    }
}
