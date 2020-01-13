package com.acme.resources;

import com.acme.beans.BooksBean;
import com.acme.books.Book;
import com.acme.consumers.BookConsumer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kumuluz.ee.logs.cdi.Log;
import com.kumuluz.ee.logs.cdi.LogParams;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("books")
@Log(LogParams.METRICS)
public class BookResource {

    @Inject
    private BooksBean booksBean;

    @Inject
    private BookConsumer consumer;

    @GET
    @Log(value = LogParams.METRICS, methodCall = true)
    @Path("add")
    public Response addBooks() {
        if (consumer.hasRecievedMessage()) {
            Book book = new Book();
            book.setAuthor("Marie Belowic");
            book.setDescription("A verry good book.");
            book.setNumPages(500);
            book.setPublishDate(new Date(System.currentTimeMillis()));
            book.setPublisher("Larry & Smith");
            book.setTitle("Cadilac autorepair manual.");
            booksBean.saveBook(book);

            book = new Book();
            book.setAuthor("John Doe");
            book.setDescription("Intriguing mistery novel.");
            book.setNumPages(320);
            book.setPublishDate(new Date(System.currentTimeMillis()));
            book.setPublisher("Claire Belle Publishing");
            book.setTitle("Blody dagger.");
            booksBean.saveBook(book);

            book = new Book();
            book.setAuthor("Henry Stevens");
            book.setDescription("Indespensable for professionals.");
            book.setNumPages(5000);
            book.setPublishDate(new Date(System.currentTimeMillis()));
            book.setPublisher("Professional publishing");
            book.setTitle("Angular: the basics.");
            booksBean.saveBook(book);

            return Response.ok("Books added.").build();
        } else {
            return Response.ok("No message recieved.").build();
        }
    }

    @GET
    @Log(value = LogParams.METRICS, methodCall = true)
    @Retry(maxRetries = 20)
    @CircuitBreaker(failureRatio = 0.2, requestVolumeThreshold = 5)
    @Path("break/{doBreak}")
    public Response testCircuitBreaker(@PathParam("doBreak") String doBreak) throws Exception {
        if (Integer.parseInt(doBreak) > 0) {
            throw new Exception();
        } else {
            return Response.ok("Klic uspel.").build();
        }
    }

    @GET
    @Log(value = LogParams.METRICS, methodCall = true)
    @Fallback(fallbackMethod = "fallbackGetAllBooks")
//    @Retry(maxRetries = 20)
//    @CircuitBreaker(failureRatio = 1, requestVolumeThreshold = 20, delay = 10000)
    public Response getAllBooks() throws JsonProcessingException {
        List<Book> books = booksBean.getBooks();
        ObjectMapper mapper = new ObjectMapper();
        StringBuilder sb = new StringBuilder("[");
        for (Book book : books) {
            sb.append(mapper.writeValueAsString(book));
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");

        return Response.ok(sb.toString()).build();
    }

    public Response fallbackGetAllBooks() {
        List<Book> books = new ArrayList<>();
        return Response.ok(books).build();
    }

    @GET
    @Fallback(fallbackMethod = "getBooksFallback")
    @Log(value = LogParams.METRICS, methodCall = true)
    @Path("{bookId}")
    public Response getBooks(@PathParam("bookId") String bookId) throws JsonProcessingException {
        String[] bookIds = bookId.split(",");

        return Response.ok(getBooksWithIds(bookIds)).build();
    }

    public Response getBooksFallback(String bookId) {
        return Response.ok(new ArrayList<>()).build();
    }

    private String getBooksWithIds(String[] ids) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        StringBuilder sb = new StringBuilder("[");
        Book book;
        for (int i = 0; i < ids.length; i++) {
            book = booksBean.get(Integer.parseInt(ids[i]));
            if (book != null) {
                sb.append(mapper.writeValueAsString(book));
                sb.append(",");
            }
        }

        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");

        return sb.toString();
    }

    @GET
    @Fallback(fallbackMethod = "existsBookFallback")
    @Log(value = LogParams.METRICS, methodCall = true)
    @Path("/exists/{bookId}")
    public Response existsBook(@PathParam("bookId") String bookId) {
        Book book = booksBean.get(Integer.parseInt(bookId));
        if (book != null) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    public Response existsBookFallback(String bookId) {
        return Response.status(Response.Status.NOT_FOUND).build();
    }

//    @GET
//    @Fallback(fallbackMethod = "getSortedBooksFallback")
//    @Log(value = LogParams.METRICS, methodCall = true)
//    @Path("/sorted/{param}")
//    public Response getSortedBooks(@PathParam("param") String param) throws Exception {
//        String[] params = param.split(",");
//        List<Book> books;
//        if (params[0].equals("all")) {
//            books = booksBean.getBooks();
//        } else if (params[0].equals("fav")) {
//            books = getBooksWithIds(Arrays.copyOfRange(params, 3, params.length));
//        } else {
//            throw new Exception();
//        }
//
//        switch (params[1]) {
//            case "title":
//                books.sort(new Comparator<Book>() {
//                    @Override
//                    public int compare(Book o1, Book o2) {
//                        return o1.getTitle().compareTo(o2.getTitle());
//                    }
//                });
//                break;
//            case "author":
//                books.sort(new Comparator<Book>() {
//                    @Override
//                    public int compare(Book o1, Book o2) {
//                        return o1.getAuthor().compareTo(o2.getAuthor());
//                    }
//                });
//                break;
//            case "description":
//                books.sort(new Comparator<Book>() {
//                    @Override
//                    public int compare(Book o1, Book o2) {
//                        return o1.getDescription().compareTo(o2.getDescription());
//                    }
//                });
//                break;
//            case "numPages":
//                books.sort(new Comparator<Book>() {
//                    @Override
//                    public int compare(Book o1, Book o2) {
//                        return Integer.toString(o1.getNumPages()).compareTo(Integer.toString(o2.getNumPages()));
//                    }
//                });
//                break;
//            case "publisher":
//                books.sort(new Comparator<Book>() {
//                    @Override
//                    public int compare(Book o1, Book o2) {
//                        return o1.getPublisher().compareTo(o2.getPublisher());
//                    }
//                });
//                break;
//            case "publishDate":
//                books.sort(new Comparator<Book>() {
//                    @Override
//                    public int compare(Book o1, Book o2) {
//                        return o1.getPublishDate().compareTo(o2.getPublishDate());
//                    }
//                });
//                break;
//            default:
//                throw new Exception();
//        }
//
//        if (params[2].equals("down")) {
//            Collections.reverse(books);
//            return Response.ok(books).build();
//        } else if (params[2].equals("up")) {
//            return Response.ok(books).build();
//        } else {
//            throw new Exception();
//        }
//    }
//
    public Response getSortedBooksFallback(String param) {
        return Response.ok(new ArrayList<>()).build();
    }

    @GET
    @Fallback(fallbackMethod = "getSortedBooksFallback")
    @Log(value = LogParams.METRICS, methodCall = true)
    @Path("/search/{param}")
    public Response getSearchBooks(@PathParam("param") String param) throws JsonProcessingException {
        ArrayList<Book> books = new ArrayList<>();
        books.addAll(booksBean.getBooksByAuthor(param));
        books.addAll(booksBean.getBooksByTitle(param));
        books.addAll(booksBean.getBooksByPublisher(param));

        ObjectMapper mapper = new ObjectMapper();
        StringBuilder sb = new StringBuilder("[");
        for (Book book : books) {
            sb.append(mapper.writeValueAsString(book));
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");

        return Response.ok(sb.toString()).build();
    }

    @POST
    @Log(value = LogParams.METRICS, methodCall = true)
    public Response addNewBook(Book book) {
        booksBean.saveBook(book);
        return Response.noContent().build();
    }
}
