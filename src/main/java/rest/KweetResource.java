package rest;

import auth.JWTStore;
import domain.Kweet;
import domain.Link;
import domain.views.KweetView;
import exceptions.KweetNotFoundException;
import exceptions.UserNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import services.KweetService;

@Path("kweet")
@Tag(name = "Kweet")
@Produces(MediaType.APPLICATION_JSON)
public class KweetResource {

    @Inject
    KweetService kweetService;

    @Inject
    JWTStore jwtStore;

    @POST
    @Path("{username}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a kweet for the given user")
    public Response add(Kweet kweet, @PathParam("username") String username) {
        try {
            kweetService.createKweet(kweet, username);
        } catch (UserNotFoundException ex) {
            throw new WebApplicationException(ex.getMessage(), Response.Status.BAD_REQUEST);
        }
        URI id = URI.create(kweet.getCreatedBy().getUsername());
        return Response.created(id).build();
    }

    @GET
    @Path("{content}")
    @Operation(summary = "Find a kweet by it's content")
    public Response findByContent(@PathParam("content") String content, @Context UriInfo uriInfo,
            @HeaderParam("Authorization") String authorization) {
        List<KweetView> kweets = kweetService.findByContent(content);
        try {
            setKweetViewLinks(kweets, uriInfo, this.jwtStore.getCredential(authorization.substring(7)).getCaller());
        } catch (Exception ex) {
            throw new WebApplicationException(ex.getMessage(), Response.Status.UNAUTHORIZED);
        }
        return Response.ok(kweets).build();
    }

    @GET
    @Path("{username}/mentions")
    @Operation(summary = "Retrieve the kweets in which the given user is mentioned")
    public Response getMentions(@PathParam("username") String username, @Context UriInfo uriInfo,
            @HeaderParam("Authorization") String authorization) {
        try {
            List<KweetView> kweets = kweetService.getMentions(username);
            try {
                setKweetViewLinks(kweets, uriInfo, this.jwtStore.getCredential(authorization.substring(7)).getCaller());
            } catch (Exception ex) {
                throw new WebApplicationException(ex.getMessage(), Response.Status.UNAUTHORIZED);
            }
            return Response.ok(kweets).build();
        } catch (UserNotFoundException ex) {
            throw new WebApplicationException(ex.getMessage(), Response.Status.BAD_REQUEST);
        }
    }

    @PUT
    @Path("{id}/like")
    @Operation(summary = "Like a kweet for the given user")
    public Response likeKweet(@PathParam("id") long id, @HeaderParam("Authorization") String authorization) {
        try {
            if (kweetService.likeKweet(id, this.jwtStore.getCredential(authorization.substring(7)).getCaller())) {
                return Response.ok().build();
            }
        } catch (UserNotFoundException | KweetNotFoundException ex) {
            throw new WebApplicationException(ex.getMessage(), Response.Status.BAD_REQUEST);
        } catch (Exception ex) {
            throw new WebApplicationException(ex.getMessage(), Response.Status.UNAUTHORIZED);
        }
        throw new WebApplicationException(Response.Status.BAD_REQUEST);
    }

    @PUT
    @Path("{id}/unlike")
    @Operation(summary = "Unlike a kweet for the given user")
    public Response unlikeKweet(@PathParam("id") long id, @HeaderParam("Authorization") String authorization) {
        try {
            if (kweetService.unlikeKweet(id, this.jwtStore.getCredential(authorization.substring(7)).getCaller())) {
                return Response.ok().build();
            }
        } catch (UserNotFoundException | KweetNotFoundException ex) {
            throw new WebApplicationException(ex.getMessage(), Response.Status.BAD_REQUEST);
        } catch (Exception ex) {
            throw new WebApplicationException(ex.getMessage(), Response.Status.UNAUTHORIZED);
        }
        throw new WebApplicationException(Response.Status.BAD_REQUEST);
    }

    @GET
    @Path("{id}/likes")
    @Operation(summary = "Get the users who like the given kweet")
    public Response getKweetLikes(@PathParam("id") long id) {
        try {
            return Response.ok(kweetService.getKweetLikes(id)).build();
        } catch (KweetNotFoundException ex) {
            throw new WebApplicationException(ex.getMessage(), Response.Status.BAD_REQUEST);
        }
    }

    @DELETE
    @Path("{id}")
    @Operation(summary = "Delete kweet by id")
    public Response deleteKweet(@HeaderParam("Authorization") String authorization, @PathParam("id") long id) {
        try {
            kweetService.deleteKweet(id, this.jwtStore.getCredential(authorization.substring(7)).getCaller());
            return Response.ok().build();
        } catch (KweetNotFoundException ex) {
            throw new WebApplicationException(ex.getMessage(), Response.Status.BAD_REQUEST);
        } catch (Exception ex) {
            throw new WebApplicationException(ex.getMessage(), Response.Status.UNAUTHORIZED);
        }
    }

    @GET
    @Path("{username}/timeline")
    @Operation(summary = "Retrieve the timeline(=his own kweets, and the kweets of people he follows) for a user")
    public Response getUserTimeline(@PathParam("username") String username, @Context UriInfo uriInfo,
            @HeaderParam("Authorization") String authorization) {
        try {
            List<KweetView> kweets = kweetService.getTimeline(username);
            try {
                setKweetViewLinks(kweets, uriInfo, this.jwtStore.getCredential(authorization.substring(7)).getCaller());
            } catch (Exception ex) {
                throw new WebApplicationException(ex.getMessage(), Response.Status.UNAUTHORIZED);
            }
            return Response.ok(kweets).build();
        } catch (UserNotFoundException ex) {
            throw new WebApplicationException(ex.getMessage(), Response.Status.BAD_REQUEST);
        }
    }

    private void setKweetViewLinks(List<KweetView> kweets, UriInfo uriInfo, String username) {
        for (KweetView kweetview : kweets) {
            String createdByUri = uriInfo.getBaseUriBuilder().path(UserResource.class)
                    .path(kweetview.getCreatedBy()).build().toString();
            String deleteKweetUri = uriInfo.getBaseUriBuilder().path(KweetResource.class)
                    .path(String.valueOf(kweetview.getId())).build().toString();
            String likeKweetUri = uriInfo.getBaseUriBuilder().path(KweetResource.class)
                    .path(String.valueOf(kweetview.getId())).path("like").build().toString();
            String unlikeKweetUri = uriInfo.getBaseUriBuilder().path(KweetResource.class)
                    .path(String.valueOf(kweetview.getId())).path("unlike").build().toString();
            kweetview.getLinks().add(new Link(createdByUri, "createdBy", "GET"));
            kweetview.getLinks().add(new Link(deleteKweetUri, "delete", "DELETE"));
            if (!kweetService.kweetLiked(username, kweetview.getId())) {
                kweetview.getLinks().add(new Link(likeKweetUri, "like", "PUT"));
            } else {
                kweetview.getLinks().add(new Link(unlikeKweetUri, "unlike", "PUT"));
            }
        }
    }

}
