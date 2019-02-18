/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import dao.JPA;
import domain.User;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import services.UserService;

@Path("user")
@Stateless
public class UserResource {

    @Inject
    UserService userService;

    @POST
    public String add(User user) {
        userService.addUser(user);
        return "User added";
    }

    @GET
    @Path("{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public User get(@PathParam("name") String name) {
        return userService.getUser(name);
    }
}
