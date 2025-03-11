package dto;

import java.util.Date;

public record CreateBook(String title, String author, String isbn, String description, Date bookPublishDate, Long bookPagesNumber) {
}
