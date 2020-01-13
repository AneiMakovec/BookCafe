package com.acme.resources;

import com.acme.comments.Comment;
import com.acme.beans.CommentsBean;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kumuluz.ee.logs.cdi.Log;
import com.kumuluz.ee.logs.cdi.LogParams;
import org.eclipse.microprofile.faulttolerance.Fallback;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Comparator;
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
    public Response getAllComments() throws JsonProcessingException {
        List<Comment> comments = commentsBean.getComments();

        ObjectMapper mapper = new ObjectMapper();
        StringBuilder sb = new StringBuilder("[");
        for (Comment c : comments) {
            sb.append(mapper.writeValueAsString(c));
            sb.append(",");
        }

        if (comments != null)
            sb.deleteCharAt(sb.length() - 1);

        sb.append("]");

        return Response.ok(sb.toString()).build();
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
    public Response getCommentByUser(@PathParam("userId") String userId) throws JsonProcessingException {
        List<Comment> comments = commentsBean.getCommentsByUser(Integer.parseInt(userId));
        if (comments == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        ObjectMapper mapper = new ObjectMapper();
        StringBuilder sb = new StringBuilder("[");
        for (Comment c : comments) {
            sb.append(mapper.writeValueAsString(c));
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");

        return Response.ok(sb.toString()).build();
    }

    @GET
    @Fallback(fallbackMethod = "getCommentOfBookFallback")
    @Log(value = LogParams.METRICS, methodCall = true)
    @Path("ofbook/{bookId}")
    public Response getCommentOfBook(@PathParam("bookId") String bookId) throws JsonProcessingException {
        List<Comment> comments = commentsBean.getCommentsOfBook(Integer.parseInt(bookId));
        if (comments == null)
            Response.status(Response.Status.NOT_FOUND).build();

        comments.sort(new Comparator<Comment>() {
            @Override
            public int compare(Comment o1, Comment o2) {
                return o1.getCreatedTimestamp().compareTo(o2.getCreatedTimestamp());
            }
        });

        ObjectMapper mapper = new ObjectMapper();
        StringBuilder sb = new StringBuilder("[");
        for (Comment c : comments) {
            sb.append(mapper.writeValueAsString(c));
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");

        return Response.ok(sb.toString()).build();
    }

    public Response getCommentOfBookFallback(String bookId) {
        return Response.ok(new ArrayList<>()).build();
    }

//    @POST
//    @Fallback(fallbackMethod = "addNewCommentFallback")
//    @Log(value = LogParams.METRICS, methodCall = true)
//    @Path("add/{userId},{bookId},{content}")
//    public Response addNewComment(@PathParam("userId") String userId, @PathParam("bookId") String bookId, @PathParam("content") String content) {
//        Comment comment = new Comment();
//        comment.setUserId(Integer.parseInt(userId));
//        comment.setBookId(Integer.parseInt(bookId));
//        comment.setContent(content);
//        comment.setCreatedTimestamp(new Time(System.currentTimeMillis()));
//
//        commentsBean.saveComment(comment);
//
//        return Response.noContent().build();
//    }

    @POST
    @Fallback(fallbackMethod = "addNewCommentFallback")
    @Log(value = LogParams.METRICS, methodCall = true)
    @Path("add")
    public Response addNewComment(Comment comment) {
        if (comment != null)
            commentsBean.saveComment(comment);

        return Response.noContent().build();
    }

    public Response addNewCommentFallback(Comment comment) {
        return Response.noContent().build();
    }
}
