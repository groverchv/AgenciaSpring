package com.AgenciaSpring.AgenciaSpring.services;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FcmService {

    /**
     * Envia una notificacion individual a un token FCM especifico.
     */
    public void sendNotification(String token, String title, String body) {
        if (token == null || token.isEmpty()) {
            return;
        }
        try {
            Message message = Message.builder()
                    .setToken(token)
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build())
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Successfully sent message: " + response);
        } catch (Exception e) {
            System.err.println("Error sending push notification to token " + token + ": " + e.getMessage());
        }
    }

    /**
     * Envia una notificacion multicast a multiples tokens FCM.
     */
    public void sendMulticastNotification(List<String> tokens, String title, String body) {
        if (tokens == null || tokens.isEmpty()) {
            return;
        }
        try {
            MulticastMessage message = MulticastMessage.builder()
                    .addAllTokens(tokens)
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build())
                    .build();

            var response = FirebaseMessaging.getInstance().sendEachForMulticast(message);
            System.out.println("Successfully sent multicast message: " + response.getSuccessCount() + " messages were sent successfully.");
        } catch (Exception e) {
            System.err.println("Error sending multicast push notification: " + e.getMessage());
        }
    }
}
