package biblotech.presentation;

import biblotech.business.BookService;
import biblotech.dto.BookListResponse;
import biblotech.dto.SortedBookPageResponse;
import biblotech.dto.BookResponse;
import biblotech.dto.CreateBook;
import biblotech.entity.Book;
import biblotech.exceptions.NullParameterError;
import biblotech.rules.ValidBookAuthor;
import biblotech.rules.ValidBookTitle;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
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

    @GET
    @Path("book-by-id-or-isbn")
    @Produces(MediaType.APPLICATION_JSON)
    public BookResponse getOneBook(@QueryParam("id") Long id, @QueryParam("isbn") String isbn){
        if (id != null) {
            return bookService.getBookById(id);
        } else if (isbn != null) {
            return bookService.getOneBookByISBN(isbn);
        }else{
            throw new NullParameterError("The id or ISBN is required least one of the required parameters");
        }
    }

    /**
     * GET /books/search?title=YourBookTitle&author=YourBookAuthor
     */
    @GET
    @Path("search")
    @Produces(MediaType.APPLICATION_JSON)
    public BookResponse getBooksByTitleAndAuthor(@Valid @ValidBookTitle @QueryParam("title")  String title, @ValidBookAuthor @QueryParam("author") String author){
            return bookService.getBookByTitleAndAuthor(title, author);
    }


    /**
     * GET /books/authors?author=StringPattern&pageNumber=Integer&pageSize=Integer
     */
    @GET
    @Path("authors")
    @Produces(MediaType.APPLICATION_JSON)
    public SortedBookPageResponse getBooksByAuthorSortedByTitleDesc(
            @Valid @ValidBookAuthor @QueryParam("author") String author,
            @Valid @Positive @QueryParam("pageNumber") Long pageNumber,
            @Valid @Positive @QueryParam("pageSize") Integer pageSize
            ){
        return bookService.getBooksByAuthor(author, pageNumber, pageSize);

    }




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
                .header("Location", "/api/books/" + newBook.getBookID())
                .build();

    }




}
