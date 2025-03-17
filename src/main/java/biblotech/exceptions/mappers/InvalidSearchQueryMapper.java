package biblotech.exceptions.mappers;

import biblotech.exceptions.InvalidSearchQuery;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidSearchQueryMapper implements ExceptionMapper<InvalidSearchQuery> {
    @Override
    public Response toResponse(InvalidSearchQuery invalidSearchQuery) {
        return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity(invalidSearchQuery.getMessage()).build();
    }
}
