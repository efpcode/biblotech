package biblotech.exceptions;

public class InvalidSearchQuery extends RuntimeException {
    public InvalidSearchQuery() {
        super();
    }
    public InvalidSearchQuery(String message) {
        super(message);
    }
}
