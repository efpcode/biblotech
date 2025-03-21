package biblotech.exceptions.mappers;

import biblotech.exceptions.InvaildDateFormat;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidDateFormatMapper implements ExceptionMapper<InvaildDateFormat> {

    @Override
    public Response toResponse(InvaildDateFormat invaildDateFormat) {
        return Response.status(Response.Status.BAD_REQUEST).entity(invaildDateFormat.getMessage()).build();
    }
}
