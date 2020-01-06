package com.acme.resources;

import com.acme.comments.Comment;
import com.acme.beans.CommentsBean;
import com.kumuluz.ee.logs.cdi.Log;
import com.kumuluz.ee.logs.cdi.LogParams;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
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

    @GET
    @Log(value = LogParams.METRICS, methodCall = true)
    @Path("{commentId}")
    public Response getComment(@PathParam("commentId") String commentId) {
        Comment comment = commentsBean.get(Integer.parseInt(commentId));
        return comment != null
                ? Response.ok(comment).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Log(value = LogParams.METRICS, methodCall = true)
    @Path("byuser/{userId}")
    public Response getCommentByUser(@PathParam("userId") String userId) {
        List<Comment> comments = commentsBean.getCommentsByUser(Integer.parseInt(userId));
        return comments != null
                ? Response.ok(comments).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Log(value = LogParams.METRICS, methodCall = true)
    @Path("ofbook/{bookId}")
    public Response getCommentOfBook(@PathParam("bookId") String bookId) {
        List<Comment> comments = commentsBean.getCommentsOfBook(Integer.parseInt(bookId));
        return comments != null
                ? Response.ok(comments).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Log(value = LogParams.METRICS, methodCall = true)
    public Response addNewComment(Comment comment) {
        commentsBean.saveComment(comment);
        return Response.noContent().build();
    }
}
