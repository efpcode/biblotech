package biblotech.dto;

public record PatchBook(
        String title,
        String author,
        String isbn,
        String description,
        String publishedYear,
        String pages
) {
}
