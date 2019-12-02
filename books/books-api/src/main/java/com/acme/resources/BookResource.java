package com.acme.resources;

import com.acme.beans.BooksBean;
import com.acme.books.Book;

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
@Path("/books")
public class BookResource {

    @Inject
    private BooksBean booksBean;

    @GET
    public Response getAllBooks() {
        List<Book> books = booksBean.getBooks();
        return Response.ok(books).build();
    }
}
