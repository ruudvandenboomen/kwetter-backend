/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import domain.User;
import domain.views.ProfileView;
import exceptions.UserNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.net.URI;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import services.UserService;

@Api("User")
@Path("user")
public class UserResource {

    @Inject
    UserService userService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Creates a user")
    public Response add(User user) {
        userService.addUser(user);
        URI id = URI.create(user.getUsername());
        return Response.created(id).build();
    }

    @GET
    @Path("{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve a user his profile by it's username")
    public Response getByName(@PathParam("username") String username) {
        ProfileView profile = null;
        try {
            profile = userService.getProfile(username);
        } catch (UserNotFoundException ex) {
            throw new WebApplicationException(ex.getMessage(), Response.Status.NOT_FOUND);
        }
        return Response.ok(profile).build();
    }

    @GET
    @Path("{username}/followers")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve a user his followers by it's name")
    public Response getFollowers(@PathParam("username") String username) {
        List<String> followers;
        try {
            followers = userService.getFollowers(username);
        } catch (UserNotFoundException ex) {
            throw new WebApplicationException(ex.getMessage(), Response.Status.NOT_FOUND);
        }
        return Response.ok(followers).build();
    }

    @GET
    @Path("{username}/following")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve a user his follwing by it's name")
    public Response getFollowing(@PathParam("username") String username) {
        List<String> following;
        try {
            following = userService.getFollowing(username);
        } catch (UserNotFoundException ex) {
            throw new WebApplicationException(ex.getMessage(), Response.Status.NOT_FOUND);
        }
        return Response.ok(following).build();
    }

    @PUT
    @Path("{username}/following/add/{userToFollow}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Add a follower to a user")
    public Response follow(@PathParam("username") String username, @PathParam("userToFollow") String userToFollow) {
        if (userService.addFollow(username, userToFollow)) {
            return Response.ok().build();
        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
}
