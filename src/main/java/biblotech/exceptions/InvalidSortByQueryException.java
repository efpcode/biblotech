package biblotech.exceptions;

public class InvalidSortByQueryException extends RuntimeException {
  public InvalidSortByQueryException() {
    super();
  }
    public InvalidSortByQueryException(String message) {
        super(message);
    }
}
