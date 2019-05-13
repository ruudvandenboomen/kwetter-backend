/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import domain.views.KweetView;
import java.io.StringReader;
import java.util.Date;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import services.UserService;

public class KweetDecoder implements Decoder.Text<KweetView> {

    @Inject
    private UserService userservice;

    @Override
    public KweetView decode(final String textMessage) throws DecodeException {
        KweetView kweet = new KweetView();
        JsonObject jsonObject = Json.createReader(new StringReader(textMessage)).readObject();
        kweet.setId(Long.valueOf(jsonObject.getString("id")));
        kweet.setContent(jsonObject.getString("content"));
        kweet.setPostedOn(new Date(jsonObject.getString("postedOn")));
        kweet.setCreatedBy(userservice.getUserByName(jsonObject.getString("username")).getUsername());
        kweet.setLikes(jsonObject.getInt("likes"));
        return kweet;
    }

    @Override
    public boolean willDecode(String s) {
        return true;
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }

}
