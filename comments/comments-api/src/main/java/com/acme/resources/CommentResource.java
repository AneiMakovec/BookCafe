package com.acme.resources;

import com.acme.comments.Comment;
import com.acme.beans.CommentsBean;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("comments")
public class CommentResource {

    @Inject
    private CommentsBean commentsBean;

    @GET
    public Response getAllComments() {
        List<Comment> comments = commentsBean.getComments();
        return Response.ok(comments).build();
    }
}
