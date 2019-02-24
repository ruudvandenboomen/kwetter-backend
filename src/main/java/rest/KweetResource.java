package rest;

import domain.Kweet;
import domain.User;
import io.swagger.annotations.Api;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import services.KweetService;

@Path("kweets")
@Stateless
@Api
public class KweetResource {

    @Inject
    KweetService kweetService;

    @POST
    public String add(User user, Kweet kweet) {
        kweetService.createKweet(user, kweet);
        return "Kweet added";
    }

}
