package dto;

import rules.ValidBookTitle;

import java.util.Date;

public record CreateBook(
       @ValidBookTitle String title,
        String author,
        String isbn,
        String description,
        Date bookPublishDate,
        Long bookPagesNumber) {
}
