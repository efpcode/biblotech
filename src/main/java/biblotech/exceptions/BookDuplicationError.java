package biblotech.exceptions;

public class BookDuplicationError extends RuntimeException {
  public BookDuplicationError() {
    super();
  }
    public BookDuplicationError(String message) {
        super(message);
    }
}
