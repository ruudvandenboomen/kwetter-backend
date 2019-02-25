package rest;

import domain.Kweet;
import domain.User;
import io.swagger.annotations.Api;
import java.net.URI;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import services.KweetService;

@Path("kweets")
@Stateless
@Api
public class KweetResource {

    @Inject
    KweetService kweetService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(Kweet kweet) {
        kweetService.createKweet(new User(), kweet);
        URI id = URI.create(kweet.getCreatedBy().getUsername());
        return Response.created(id).build();
    }

    @GET
    @Path("{content}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByContent(@PathParam("content") String content) {
        return Response.ok(kweetService.findByContent(content)).build();
    }

}
