package com.acme.resources;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.acme.beans.FavouriteBean;
import com.acme.beans.UsersBean;
import com.acme.comments.Comment;
import com.acme.pojo.Message;
import com.acme.users.Favourite;
import com.acme.users.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kumuluz.ee.discovery.annotations.DiscoverService;
import com.kumuluz.ee.logs.cdi.Log;
import com.kumuluz.ee.logs.cdi.LogParams;
import com.kumuluz.ee.streaming.common.annotations.StreamProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.eclipse.microprofile.faulttolerance.Fallback;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("app")
@Log(LogParams.METRICS)
@RequestScoped
public class AppResource implements Serializable {

    @Inject
    private UsersBean usersBean;

    @Inject
    private FavouriteBean favouriteBean;

    @Inject
    @DiscoverService(value = "book-service", environment = "dev", version = "*")
    private Optional<WebTarget> bookTarget;

    @Inject
    @DiscoverService(value = "comment-service", environment = "dev", version = "*")
    private Optional<WebTarget> commentTarget;

    @Inject
    @StreamProducer
    private Producer producer;

    @POST
    @Path("produce")
    public Response produceMessage(Message msg) {

        ProducerRecord<String, String> record = new ProducerRecord<String, String>(msg.getTopic(), msg.getKey(), msg
                .getContent());

        producer.send(record,
                (metadata, e) -> {
                    if (e != null) {
                        e.printStackTrace();
                    } else {
                        System.out.println("The offset of the produced message record is: " + metadata.offset());
                    }
                });

        return Response.ok().build();
    }

    @GET
    @Path("getfavourites")
    public Response getFavourites() throws JsonProcessingException {
        List<Favourite> favs = favouriteBean.getFavourites();

        ObjectMapper mapper = new ObjectMapper();
        StringBuilder sb = new StringBuilder("[");
        for (Favourite fav : favs) {
            sb.append(mapper.writeValueAsString(fav));
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");

        return Response.ok(sb.toString()).build();
    }

    @GET
    @Path("getuser/{userId}")
    @Log(value = LogParams.METRICS, methodCall = true)
    public Response getUser(@PathParam("userId") String userId) {
        User user = usersBean.get(Integer.parseInt(userId));
        if (user != null) {
            return Response.ok(user).build();
        } else {
            return Response.noContent().build();
        }
    }

    @GET
    @Path("login/{username},{password}")
    @Log(value = LogParams.METRICS, methodCall = true)
    public Response login(@PathParam("username") String userName, @PathParam("password") String password) {
        User user = usersBean.getByName(userName);
        if (user != null && user.getPassword().equals(password)) {
            return Response.ok(user).build();
        } else {
            return Response.status(Response.Status.EXPECTATION_FAILED).build();
        }
    }

    @GET
    @Fallback(fallbackMethod = "getBooksFallback")
    @Log(value = LogParams.METRICS, methodCall = true)
    @Path("getfavouritebooks/{userId}")
    public Response getFavBooks(@PathParam("userId") String userId) throws Exception {
        List<Favourite> favList = favouriteBean.getByUser(Integer.parseInt(userId));
        if (favList != null) {
            if (bookTarget.isPresent()) {
                StringBuilder sb = new StringBuilder("/");
                for (Favourite fav : favList) {
                    sb.append(fav.getBookId() + ",");
                }
                sb.deleteCharAt(sb.length() - 1);

                return bookTarget.get().path("v1/books" + sb.toString()).request().get();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            throw new Exception();
        }
    }

    public Response getBooksFallback(String userId) {
        return Response.ok(new ArrayList<>()).build();
    }

//    @GET
//    @Log(value = LogParams.METRICS, methodCall = true)
//    @Path("getallsortedbooks/{sortParam},{sortDirection}")
//    public Response getAllSortedBooks(@PathParam("sortParam") String sortParam, @PathParam("sortDirection") String sortDirection) {
//        if (bookTarget.isPresent()) {
//            return bookTarget.get().path("v1/books/sorted/all," + sortParam + "," + sortDirection).request().get();
//        } else {
//            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
//        }
//    }

//    @GET
//    @Log(value = LogParams.METRICS, methodCall = true)
//    @Path("getfavsortedbooks/{userId},{sortParam},{sortDirection}")
//    public Response getFavSortedBooks(@PathParam("userId") String userId, @PathParam("sortParam") String sortParam, @PathParam("sortDirection") String sortDirection) {
//        List<Favourite> favList = favouriteBean.getByUser(Integer.parseInt(userId));
//        if (favList != null) {
//            if (bookTarget.isPresent()) {
//                StringBuilder sb = new StringBuilder("");
//                for (Favourite fav : favList) {
//                    sb.append(fav.getBookId() + ",");
//                }
//
//                if (sb.length() > 0)
//                    sb.deleteCharAt(sb.length() - 1);
//                else
//                    return Response.ok(new ArrayList<>()).build();
//
//                return bookTarget.get().path("v1/books/sorted/fav," + sortParam + "," + sortDirection + "," + sb.toString()).request().get();
//            } else {
//                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
//            }
//        } else {
//            return Response.ok(new ArrayList<>()).build();
//        }
//    }

    @POST
    @Fallback(fallbackMethod = "addFavouriteFallback")
    @Log(value = LogParams.METRICS, methodCall = true)
    @Path("addfavourite")
    public Response addFavourite(Favourite fav) {
        System.out.println(fav.toString());
        if (bookTarget.isPresent()) {
            if (usersBean.get(fav.getUserId()) != null && bookTarget.get().path("v1/books/exists/" + fav.getBookId()).request().get().getStatus() == Response.Status.OK.getStatusCode()) {
//                Favourite fav = new Favourite();
//                fav.setUserId(Integer.parseInt(userId));
//                fav.setBookId(Integer.parseInt(bookId));
                List<Favourite> favs = favouriteBean.getByUser(fav.getUserId());
                for (Favourite f : favs) {
                    if (f.getBookId() == fav.getBookId()) {
//                        return Response.status(Response.Status.BAD_REQUEST).build();
                        return Response.noContent().build();
                    }
                }

                favouriteBean.saveFavourite(fav);
                return Response.noContent().build();
            } else {
//                return Response.status(Response.Status.BAD_REQUEST).build();
                return Response.noContent().build();
            }
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    public Response addFavouriteFallback(Favourite fav) {
//        return Response.status(Response.Status.BAD_REQUEST).build();
        return Response.noContent().build();
    }

//    @GET
//    @Fallback(fallbackMethod = "addFavouriteFallback")
//    @Log(value = LogParams.METRICS, methodCall = true)
//    @Path("addfavourite/{userId},{bookId}")
//    public Response addFavourite(@PathParam("userId") String userId, @PathParam("bookId") String bookId) {
//        if (bookTarget.isPresent()) {
//            if (usersBean.get(Integer.parseInt(userId)) != null && bookTarget.get().path("v1/books/exists/" + bookId).request().get().getStatus() == Response.Status.OK.getStatusCode()) {
//                List<Favourite> favs = favouriteBean.getByUser(Integer.parseInt(userId));
//                for (Favourite f : favs) {
//                    if (f.getBookId() == Integer.parseInt(bookId)) {
//                        return Response.status(Response.Status.BAD_REQUEST).build();
//                    }
//                }
//
//                Favourite fav = new Favourite();
//                fav.setUserId(Integer.parseInt(userId));
//                fav.setBookId(Integer.parseInt(bookId));
//
//                favouriteBean.saveFavourite(fav);
//                return Response.ok().build();
//            } else {
//                return Response.status(Response.Status.BAD_REQUEST).build();
//            }
//        } else {
//            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//    public Response addFavouriteFallback(String userId, String bookId) {
//        return Response.status(Response.Status.BAD_REQUEST).build();
//    }

    @POST
    @Fallback(fallbackMethod = "addCommentFallback")
    @Log(value = LogParams.METRICS, methodCall = true)
    @Path("addcomment")
    public Response addComment(Comment comment) {
        if (bookTarget.isPresent() && commentTarget.isPresent()) {
            if (usersBean.get(comment.getUserId()) != null && bookTarget.get().path("v1/books/exists/" + comment.getBookId()).request().get().getStatus() == Response.Status.OK.getStatusCode()) {
//                Comment comment = new Comment();
//                comment.setUserId(Integer.parseInt(userId));
//                comment.setBookId(Integer.parseInt(bookId));
//                comment.setContent(content);
//                comment.setCreatedTimestamp(new Date(System.currentTimeMillis()));

                return commentTarget.get().path("v1/comments/add").request().header("Accept", "application/json").post(Entity.json(comment));
            } else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    public Response addCommentFallback(Comment comment) {
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Fallback(fallbackMethod = "getWeatherDataFallback")
    @Path("weather")
    public Response getWeatherData() throws IOException {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(
                "http://api.openweathermap.org/data/2.5/weather")
                .queryParam("cnt", "10")
                .queryParam("mode", "json")
                .queryParam("units", "metric")
                .queryParam("appid", "09f0b4cf6235d16e292d6515725ffcba");

        return target.queryParam("id", "3196359").request(MediaType.APPLICATION_JSON).get();
    }

    public Response getWeatherDataFallback() {
        return Response.noContent().build();
    }
}
