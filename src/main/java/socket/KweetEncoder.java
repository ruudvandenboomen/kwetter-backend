/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import domain.Kweet;
import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class KweetEncoder implements Encoder.Text<Kweet> {

    @Override
    public String encode(Kweet kweet) throws EncodeException {
        return Json.createObjectBuilder()
                .add("id", kweet.getId())
                .add("content", kweet.getContent())
                .add("username", kweet.getCreatedBy().getUsername())
                .add("postedOn", kweet.getPostedOn().toString())
                .add("likes", kweet.getLikes().size())
                .build().toString();
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }
}
