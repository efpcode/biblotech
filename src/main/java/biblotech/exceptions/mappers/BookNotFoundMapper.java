package biblotech.exceptions.mappers;
import biblotech.exceptions.BookNotFound;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class BookNotFoundMapper implements ExceptionMapper<BookNotFound> {
    @Override
    public Response toResponse(BookNotFound bookNotFound) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(bookNotFound.getMessage())
                .build();
    }
}
