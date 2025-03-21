package biblotech.exceptions;

public class InvaildDateFormat extends RuntimeException {
    public InvaildDateFormat() {
        super();
    }
    public InvaildDateFormat(String message) {
        super(message);
    }
}
