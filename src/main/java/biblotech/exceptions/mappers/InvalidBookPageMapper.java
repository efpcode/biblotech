package biblotech.exceptions.mappers;

import biblotech.exceptions.InvalidBookPage;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidBookPageMapper implements ExceptionMapper<InvalidBookPage> {
    @Override
    public Response toResponse(InvalidBookPage invalidBookPage) {
        return Response.status(Response.Status.BAD_REQUEST).entity(invalidBookPage.getMessage()).build();
    }
}
