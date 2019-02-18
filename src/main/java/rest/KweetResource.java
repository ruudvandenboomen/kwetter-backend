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
    public String add(Kweet kweet) {
        kweetService.addTweet(kweet);
        return "Kweet added";
    }

}
