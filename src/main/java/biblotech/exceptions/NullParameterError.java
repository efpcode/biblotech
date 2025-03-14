package biblotech.exceptions;

public class NullParameterError extends RuntimeException {
  public NullParameterError() {
    super();
  }
    public NullParameterError(String message) {
        super(message);
    }
}
