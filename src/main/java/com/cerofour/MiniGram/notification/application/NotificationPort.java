package com.cerofour.MiniGram.notification.application;

public interface NotificationPort {
    void suscribeToEmailService(String email);
    void sendNotification(String email, String message);
}
