package com.heylisten.backend;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    // Lista concurrente para almacenar las sesiones activas de los usuarios
    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    // Se ejecuta cuando un usuario abre el chat en la app
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("[WebSocket] Nueva conexión establecida. ID: " + session.getId());
    }

    // Se ejecuta cuando llega un mensaje de texto desde Flutter
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("[WebSocket] Mensaje recibido: " + message.getPayload());

        // Reenviamos el mensaje a todos los usuarios conectados MENOS al que lo envió
        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen() && !webSocketSession.getId().equals(session.getId())) {
                try {
                    webSocketSession.sendMessage(message);
                } catch (IOException e) {
                    System.err.println("Error enviando mensaje a la sesión: " + webSocketSession.getId());
                    e.printStackTrace();
                }
            }
        }
    }

    // Se ejecuta cuando el usuario cierra la pestaña o se desconecta
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("[WebSocket] Conexión cerrada. ID: " + session.getId());
    }
}