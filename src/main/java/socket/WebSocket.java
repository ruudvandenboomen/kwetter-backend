/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import domain.Kweet;
import event.KweetEvent;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.event.Observes;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/websocket",
        encoders = KweetEncoder.class,
        decoders = KweetDecoder.class)

public class WebSocket {

    private static final Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void onOpen(Session peer) {
        peers.add(peer);
    }

    @OnClose
    public void onClose(Session peer) {
        peers.remove(peer);
    }

    @OnMessage
    public void onMessage(String message, Session client) throws IOException, EncodeException {
        for (Session peer : peers) {
            peer.getBasicRemote().sendObject(message);
        }
    }

    public void sendToAllSessions(Kweet kweet) throws EncodeException {
        try {
            for (Session peer : peers) {
                peer.getBasicRemote().sendObject(kweet);
            }
        } catch (IOException ex) {
            Logger.getLogger(WebSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendToAllSessions(@Observes KweetEvent kweetEvent) {
        try {
            for (Session peer : peers) {
                peer.getBasicRemote().sendObject(kweetEvent.getKweet());
            }
        } catch (IOException | EncodeException ex) {
            Logger.getLogger(WebSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @OnError
    public void onError(Throwable t) {
        System.out.println(t.getCause());
    }
}
