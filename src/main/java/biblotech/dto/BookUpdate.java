package biblotech.dto;

public record BookUpdate(
        Long id,
        String title,
        String author,
        String isbn,
        String description,
        String publishedYear,
        String pages
        ) {
}
