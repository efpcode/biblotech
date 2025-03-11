package presentation;

import business.BookService;
import dto.BookListResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.java.Log;


@Path("books")
@Log
public class BookResource {

    private BookService bookService;
    public BookResource() {

    }

    @Inject
    public BookResource(BookService bookService) {
        this.bookService = bookService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public BookListResponse getBooks() {
        return new BookListResponse(bookService.getAllBooks());
    }



}
