package biblotech.exceptions.mappers;

import biblotech.exceptions.InvaildDateFormat;
import jakarta.json.bind.JsonbException;
import jakarta.json.stream.JsonParsingException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GeneralExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        Throwable rootCause = findRootCause(exception);


        if (rootCause instanceof InvaildDateFormat) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(rootCause.getMessage())
                    .build();
        }

        if (rootCause instanceof JsonbException) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid JSON input: " + rootCause.getMessage())
                    .build();
        }

        if (rootCause instanceof JsonParsingException) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Malformed JSON: Ensure all string fields, including dates, are enclosed in double quotes.")
                    .build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("An unexpected error occurred: " + exception.getMessage())
                .build();
    }

    private Throwable findRootCause(Throwable exception) {
        Throwable cause = exception;
        while (cause.getCause() != null && cause != cause.getCause()) {
            cause = cause.getCause();
        }
        return cause;
    }
}
