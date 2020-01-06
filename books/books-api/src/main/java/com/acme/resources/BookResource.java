package com.acme.resources;

import com.acme.beans.BooksBean;
import com.acme.books.Book;
import com.kumuluz.ee.logs.cdi.Log;
import com.kumuluz.ee.logs.cdi.LogParams;
//import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
//import org.eclipse.microprofile.faulttolerance.Fallback;
//import org.eclipse.microprofile.faulttolerance.Retry;
//import org.eclipse.microprofile.faulttolerance.Timeout;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
//import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("books")
@Log(LogParams.METRICS)
public class BookResource {

//    private int count = 0;

    @Inject
    private BooksBean booksBean;

    @GET
    @Log(value = LogParams.METRICS, methodCall = true)
//    @Timeout(value = 5000)
//    @Fallback(fallbackMethod = "fallbackGetAllBooks")
//    @Retry(maxRetries = 20)
//    @CircuitBreaker(failureRatio = 1, requestVolumeThreshold = 20, delay = 10000)
    public Response getAllBooks() throws TimeoutException {
//        if (count < 20) {
//            count++;
//            throw new TimeoutException();
//        } else {
//            List<Book> books = booksBean.getBooks();
//            return Response.ok(books).build();
//        }
        List<Book> books = booksBean.getBooks();
        return Response.ok(books).build();
    }

//    public Response fallbackGetAllBooks() {
//        List<Book> books = new ArrayList<>();
//        return Response.ok(books).build();
//    }

    @GET
    @Log(value = LogParams.METRICS, methodCall = true)
    @Path("{bookId}")
    public Response getBook(@PathParam("bookId") String bookId) {
        Book book = booksBean.get(Integer.parseInt(bookId));
        return book != null
                ? Response.ok(book).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Log(value = LogParams.METRICS, methodCall = true)
    public Response addNewBook(Book book) {
        booksBean.saveBook(book);
        return Response.noContent().build();
    }
}
