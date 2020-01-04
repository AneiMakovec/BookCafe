package com.acme.resources;

import com.acme.comments.Comment;
import com.acme.beans.CommentsBean;
import com.kumuluz.ee.logs.cdi.Log;
import com.kumuluz.ee.logs.cdi.LogParams;

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
@Log(LogParams.METRICS)
public class CommentResource {

    @Inject
    private CommentsBean commentsBean;

    @GET
    @Log(value = LogParams.METRICS, methodCall = true)
    public Response getAllComments() {
        List<Comment> comments = commentsBean.getComments();
        return Response.ok(comments).build();
    }
}
