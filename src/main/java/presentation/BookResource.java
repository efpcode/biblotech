package presentation;

import business.BookService;
import dto.BookListResponse;
import dto.BookResponse;
import dto.CreateBook;
import entity.Book;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public BookResponse getBook(@PathParam("id") Long id){return bookService.getBookById(id);}


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBook(@Valid CreateBook book) {
        if (book == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Book newBook = bookService.createBook(book);
        log.info("Book created: " + newBook);
        return Response
                .status(Response.Status.CREATED)
                .header("Location", "/api/books" + newBook.getBookID())
                .build();

    }



}
