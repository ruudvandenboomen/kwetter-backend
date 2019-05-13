/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import auth.JWTStore;
import domain.User;
import event.KweetEvent;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/websocket/{token}",
        encoders = KweetEncoder.class,
        decoders = KweetDecoder.class)

public class WebSocket {

    private static final Map<String, Set<Session>> sessions = Collections.synchronizedMap(new HashMap<>());

    @Inject
    JWTStore jwtStore;

    @OnOpen
    public void onOpen(@PathParam("token") String token, Session peer) {
        String username = getUsername(token);
        if (username != null) {
            if (sessions.get(username) != null) {
                Set<Session> set = sessions.get(username);
                set.add(peer);
                sessions.put(username, set);
            } else {
                Set<Session> set = new HashSet<>();
                set.add(peer);
                sessions.put(username, set);
            }
        }
    }

    @OnClose
    public void onClose(@PathParam("token") String token, Session peer) {
        String username = getUsername(token);
        if (username != null) {
            for (Session session : sessions.get(username)) {
                if (session.getId().equals(peer.getId())) {
                    sessions.get(username).remove(session);
                }
            }
        }
    }

    @OnMessage
    public void onMessage(String message, Session client) throws IOException, EncodeException {
    }

    public void sendToAllSessions(@Observes KweetEvent kweetEvent) {
        for (User user : kweetEvent.getKweet().getCreatedBy().getFollowers()) {
            if (sessions.get(user.getUsername()) != null) {
                for (Session session : sessions.get(user.getUsername())) {
                    try {
                        session.getBasicRemote().sendObject(kweetEvent.getKweet());
                    } catch (IOException | EncodeException ex) {
                        Logger.getLogger(WebSocket.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    @OnError
    public void onError(Throwable t) {
        System.out.println(t.getCause());
    }

    private String getUsername(String token) {
        String username = null;
        try {
            username = this.jwtStore.getCredential(token).getCaller();
        } catch (Exception ex) {
            Logger.getLogger(WebSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return username;
    }
}
