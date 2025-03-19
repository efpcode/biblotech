package biblotech.exceptions;

public class InvalidBookPage extends RuntimeException {
  public InvalidBookPage() {
    super();
  }
    public InvalidBookPage(String message) {
        super(message);
    }
}
