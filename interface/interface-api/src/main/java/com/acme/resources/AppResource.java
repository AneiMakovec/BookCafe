package com.acme.resources;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.acme.users.User;
import com.kumuluz.ee.discovery.annotations.DiscoverService;
import com.kumuluz.ee.logs.cdi.Log;
import com.kumuluz.ee.logs.cdi.LogParams;

import java.io.Serializable;
import java.util.Optional;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("app")
@Log(LogParams.METRICS)
@RequestScoped
public class AppResource implements Serializable {

    private User user = null;

    @Inject
    @DiscoverService(value = "book-service", environment = "dev", version = "*")
    private Optional<WebTarget> bookTarget;

    @Inject
    @DiscoverService(value = "comment-service", environment = "dev", version = "*")
    private Optional<WebTarget> commentTarget;

    @POST
    @Path("login")
    @Log(value = LogParams.METRICS, methodCall = true)
    public Response login(User user) {
        if (user == null) {
            System.out.println("NULLNULLNULLNULLNULLNULLNULLNULLNULLNULLNULLNULLNULLNULLNULLNULLNULLNULLNULLNULLNULL");
            return Response.noContent().build();
        } else {
            this.user = user;
            return Response.ok("Login successful.").build();
        }
    }

    @GET
    @Log(value = LogParams.METRICS, methodCall = true)
    @Path("getcomments")
    public Response getComments() {
        if (user != null && commentTarget.isPresent()) {
            WebTarget commentService = commentTarget.get().path("v1/comments/byuser/" + user.getUser_id());
            return commentService.request().get();
        } else {
            return Response.ok("Not logged in.").build();
        }
    }
}
