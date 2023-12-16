package org.example.websockets;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint("/websocket/hub")
public class WebSocketHub {
    private static Set<Session> sessions = new HashSet<>();

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        System.out.println("New connection opened. Total connections: " + sessions.size());
    }


    @OnMessage
    public void onMessage(String message, Session session) {
        // Broadcast the received message to all connected clients
        for (Session s : sessions) {
            try {
                s.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
        System.out.println("Connection closed. Total connections: " + sessions.size());
    }

    @OnError
    public void onError(Throwable error) {
        error.printStackTrace();
    }
}
