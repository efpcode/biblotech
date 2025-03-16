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
import biblotech.rules.ValidSortedBookOrder;
import biblotech.rules.ValidSortedBookQuery;
import jakarta.data.repository.Param;
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
    @Path("isbn/{isbn}")
    @Produces(MediaType.APPLICATION_JSON)
    public BookResponse getBookISBN(@PathParam("isbn") String isbn){
            return bookService.getOneBookByISBN(isbn);
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
     * GET /books/authors?author=A&pageNumber=2&pageSize=3&sortBy=title&sortOrder=asc
     */
    @GET
    @Path("authors")
    @Produces(MediaType.APPLICATION_JSON)
    public SortedBookPageResponse getBooksByAuthorSortedByTitleDesc(
            @Valid @ValidBookAuthor @QueryParam("author") String author,
            @Valid @Positive @QueryParam("pageNumber") Long pageNumber,
            @Valid @ValidSortedBookQuery @QueryParam("sortBy") @DefaultValue("title") String sort,
            @Valid @ValidSortedBookOrder @QueryParam("sortOrder") @DefaultValue("asc") String sortOrder,
            @Valid @Positive @QueryParam("pageSize") Integer pageSize
            ){
        return bookService.getBooksByAuthor(author, sort, sortOrder ,pageNumber, pageSize);

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
