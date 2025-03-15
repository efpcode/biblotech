package biblotech.exceptions.mappers;

import biblotech.exceptions.InvalidSortByQueryException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidSortOrderQueryExceptionMapper implements ExceptionMapper<InvalidSortByQueryException> {
    @Override
    public Response toResponse(InvalidSortByQueryException invalidSortByQueryException) {
        return Response.status(Response.Status.BAD_REQUEST).entity(invalidSortByQueryException.getMessage()).build();
    }
}
