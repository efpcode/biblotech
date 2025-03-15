package biblotech.exceptions;

public class InvalidSortOrderQueryException extends RuntimeException {
  public InvalidSortOrderQueryException() {
    super();
  }
    public InvalidSortOrderQueryException(String message) {
        super(message);
    }
}
