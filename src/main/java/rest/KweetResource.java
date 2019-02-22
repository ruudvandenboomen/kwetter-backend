package rest;

import domain.Kweet;
import domain.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import services.KweetService;

@Path("kweets")
@Stateless
public class KweetResource {

    @Inject
    KweetService kweetService;

    @GET
    public List<Kweet> all() {
        return kweetService.getTweets();
    }

    @POST
    public String add(User user, Kweet kweet) {
        kweetService.addTweet(user, kweet);
        return "Kweet added";
    }

}
