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

import static biblotech.mapper.BookMapper.mapToUpdateOrPatchBook;


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
               searchQuery
        );
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
                  searchSort
          );

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
        var oldBookUpdate = mapToUpdateOrPatchBook(oldBook, book);

        Book updateBook = bookService.updateBook(book, id);
        var newUpdateBook = mapToUpdateOrPatchBook(updateBook, book);


        if (newUpdateBook.equals(oldBookUpdate)) {
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

        }else if(book.isAllFieldsEmpty()){
            return Response.status(Response.Status.OK).entity("{message: Empty_Json_object_no_updates_were_performed, data:" + book).build();
        }

        var oldBook  = bookService.getBook(id);
        var oldBookUpdate = mapToUpdateOrPatchBook(oldBook, book);

        Book patchedBook = bookService.patchBook(book, id);
        var newBook = mapToUpdateOrPatchBook(patchedBook, book);
        if (newBook.equals(oldBookUpdate)) {
            log.info("No content change for Book patched: " + patchedBook);
            return Response.status(Response.Status.NO_CONTENT).entity("{message: No_update_was_performed, data: }"+ book).build();
        }
        log.info("Book patched with the following: " + patchedBook);
        return Response
                .status(Response.Status.OK)
                .header("Location /api/books/", id)
                .build();
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBook(@PathParam("id") Long id) {
        bookService.deleteBook(id);
        return Response.status(Response.Status.NO_CONTENT).entity("Deletion_Event_Performed").build();

    }

}
