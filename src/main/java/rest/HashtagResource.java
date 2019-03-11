/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import services.HashtagService;

@Api("Hashtag")
@Path("hashtag")
public class HashtagResource {

    @Inject
    HashtagService hashtagService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Finds the trending hashtags")
    public Response getPopularHashtags() {
        return Response.ok(hashtagService.getPopularHashtags()).build();
    }
}
