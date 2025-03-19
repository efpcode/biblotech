package biblotech.presentation;

import biblotech.business.BookService;
import biblotech.dto.*;
import biblotech.entity.Book;
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
    public  SortedBookPageResponse getBooksBySearchQuery(
            @Valid  @BeanParam BookFilterQueryResponse searchQuery

            ){

        return bookService.getBookBySearchQuery(
               searchQuery.getTitle(),
               searchQuery.getAuthor(),
               searchQuery.getPageNumber(),
               searchQuery.getPageSize(),
               searchQuery.getSortBy(),
               searchQuery.getSortOrder(),
               searchQuery.getStartDate(),
               searchQuery.getEndDate());
    }


    /**
     * GET /books/authors?author=A&pageNumber=2&pageSize=3&sortBy=title&sortOrder=asc
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public SortedBookPageResponse getBooksByAuthorSortedByTitle(
            @Valid @BeanParam BookAuthorsQueryResponse searchSort
            ){

          return bookService.getBooksSorted(
                  searchSort.getAuthor(),
                  searchSort.getTitle(),
                  searchSort.getSortBy(),
                  searchSort.getSortOrder(),
                  searchSort.getPageNumber(),
                  searchSort.getPageSize());

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
