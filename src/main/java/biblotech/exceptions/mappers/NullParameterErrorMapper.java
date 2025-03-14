package biblotech.exceptions.mappers;

import biblotech.exceptions.NullParameterError;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NullParameterErrorMapper implements ExceptionMapper<NullParameterError> {
    @Override
    public Response toResponse(NullParameterError e) {
        return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
}
