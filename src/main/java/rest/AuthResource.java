/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.nimbusds.jose.JOSEException;
import domain.User;
import exceptions.InvalidLoginException;
import exceptions.InvalidUserException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;
import services.AuthService;

@Path("auth")
@Tag(name = "Auth")
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    AuthService authService;

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(JsonObject credential) {
        try {
            String username = credential.getString("username");
            String password = credential.getString("password");
            String token = authService.login(username, password);
            return Response.ok().header(AUTHORIZATION, "Bearer " + token).build();
        } catch (InvalidLoginException ex) {
            return Response.status(UNAUTHORIZED).build();
        } catch (JOSEException ex) {
            Logger.getLogger(AuthResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(UNAUTHORIZED).build();
        }
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a user")
    public Response add(User user) {
        try {
            authService.addUser(user);
        } catch (InvalidUserException ex) {
            throw new WebApplicationException(ex.getMessage(), Response.Status.BAD_REQUEST);
        }
        URI id = URI.create(user.getUsername());
        return Response.created(id).build();
    }

}
