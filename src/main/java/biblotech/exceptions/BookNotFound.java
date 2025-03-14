package biblotech.exceptions;

public class BookNotFound extends RuntimeException {

    public  BookNotFound() {
        super();
    }

    public BookNotFound(String message) {
        super(message);
    }
}
