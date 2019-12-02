package com.acme.resources;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.acme.info.Info;
import com.kumuluz.ee.discovery.annotations.DiscoverService;

import java.util.Optional;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("info")
public class InfoResource {

    @Inject
    @DiscoverService(value = "book-service", environment = "dev", version = "*")
    private WebTarget bookService;

    @Inject
    @DiscoverService(value = "comment-service", environment = "dev", version = "*")
    private WebTarget commentService;

    @GET
    public Response getInfo() {
        Info info = new Info("Projekt implementira aplikacijo za diskutiranje in komentiranje knjig.");

        info.addClan("am7761");

        info.addMikrostoritev(bookService.getUri().toString() + "/v1/books");
        info.addMikrostoritev(commentService.getUri().toString() + "/v1/comments");

        // TODO: add gitHub
        info.addGitHub("https://github.com/AneiMakovec/BookCafe");
        info.addGitHub("https://github.com/AneiMakovec/BookCafe");

        // TODO: add travis
        info.addTravis("https://travis-ci.org/AneiMakovec/BookCafe");
        info.addTravis("https://travis-ci.org/AneiMakovec/BookCafe");

        // TODO: add dockerHub
        info.addDockerHub("https://cloud.docker.com/repository/docker/amakovec/rso-book-cafe-books");
        info.addDockerHub("https://cloud.docker.com/repository/docker/amakovec/rso-book-cafe-comments");

        return Response.ok(info).build();
    }
}
