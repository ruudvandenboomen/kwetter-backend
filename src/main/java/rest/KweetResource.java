package rest;

import domain.Kweet;
import domain.User;
import exceptions.KweetNotFoundException;
import exceptions.UserNotFoundException;
import io.swagger.annotations.Api;
import java.net.URI;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import services.KweetService;

@Api
@Path("kweet")
public class KweetResource {

    @Inject
    KweetService kweetService;

    @POST
    @Path("{username}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(Kweet kweet, @PathParam("username") String username) {
        try {
            kweetService.createKweet(kweet, username);
        } catch (UserNotFoundException ex) {
            Logger.getLogger(KweetResource.class.getName()).log(Level.SEVERE, null, ex);
        }
//        URI id = URI.create(kweet.getCreatedBy().getUsername());
        return Response.ok().build();
    }

    @GET
    @Path("{content}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByContent(@PathParam("content") String content) {
        return Response.ok(kweetService.findByContent(content)).build();
    }

    @GET
    @Path("{username}/mentions")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMentions(@PathParam("username") String username) {
        try {
            return Response.ok(kweetService.getMentions(username)).build();
        } catch (UserNotFoundException ex) {
            Logger.getLogger(KweetResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new WebApplicationException(Response.Status.BAD_REQUEST);
    }

    @PUT
    @Path("{id}/like/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response likeKweet(@PathParam("id") long id, @PathParam("username") String username) {
        try {
            if (kweetService.likeKweet(id, username)) {
                return Response.ok().build();
            }
        } catch (UserNotFoundException ex) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        } catch (KweetNotFoundException ex) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        throw new WebApplicationException(Response.Status.BAD_REQUEST);
    }

}
