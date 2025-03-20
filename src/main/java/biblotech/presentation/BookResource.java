package biblotech.presentation;

import biblotech.business.BookService;
import biblotech.dto.*;
import biblotech.entity.Book;
import biblotech.mapper.BookMapper;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.java.Log;

import static biblotech.mapper.BookMapper.fromUpdateOrPatchBook;
import static biblotech.mapper.BookMapper.mapToUpdate;


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
                .header("Location /api/books/", newBook.getBookID())
                .build();

    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBook(@Valid UpdateBook book, @PathParam("id") Long id) {
        if (book == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }



        var oldBook  = bookService.getBook(id);
        var oldBookUpdate = mapToUpdate(oldBook);

        Book updateBook = bookService.updateBook(book, id);
        var newUpdateBook = mapToUpdate(updateBook);
        System.out.println("Book "+ oldBookUpdate);
        System.out.println("Book updated: " + newUpdateBook);

        System.out.println("newUpdateBook.equals(book) answer: " + newUpdateBook.equals(oldBookUpdate));


        if (newUpdateBook.equals(oldBookUpdate) || book.isAllFieldsEmpty()) {
            log.info("No content change for Book update: " + updateBook);
            return Response.status(Response.Status.NO_CONTENT).build();

        }
        log.info("Book updated with the following: " + updateBook);
        return Response
                .status(Response.Status.OK)
                .header("Location /api/books/", id).build();
    }



    @PATCH
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response patchBook(@Valid PatchBook book, @PathParam("id") Long id) {
        if (book == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Book patchedBook = bookService.patchBook(book, id);
        log.info("Book patched with the following: " + patchedBook);
        return Response
                .status(Response.Status.OK)
                .header("Location /api/books/", id)
                .build();
    }





}
