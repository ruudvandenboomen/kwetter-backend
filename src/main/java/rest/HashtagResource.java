/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import services.HashtagService;

@Tag(name = "Hashtag")
@Path("hashtag")
public class HashtagResource {

    @Inject
    HashtagService hashtagService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Find the trending hashtags")
    public Response getPopularHashtags() {
        return Response.ok(hashtagService.getPopularHashtags()).build();
    }
}
