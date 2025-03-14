package biblotech.exceptions.mappers;

import biblotech.exceptions.BookDuplicationError;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class BookDuplicationErrorMapper implements ExceptionMapper<BookDuplicationError> {

    @Override
    public Response toResponse(BookDuplicationError bookDuplicationError) {
        return Response.status(Response.Status.CONFLICT).entity(bookDuplicationError.getMessage()).build();
    }
}
