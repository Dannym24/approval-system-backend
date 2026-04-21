package com.prueba.approval_system.provider;

import org.springframework.stereotype.Service;

@Service
public class EmailNotificationProvider implements NotificationProvider {

    @Override
    public void sendNotification(String email, String message) {
        System.out.println("📧 [EMAIL PROVIDER]");
        System.out.println("To: " + email);
        System.out.println("Message: " + message);
    }
}