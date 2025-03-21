package biblotech.dto;

import biblotech.rules.*;

import biblotech.rules.util.DateDeserializer;
import jakarta.json.bind.annotation.JsonbTypeDeserializer;
import jakarta.validation.constraints.*;

public record CreateBook(
        @ValidBookTitle @NotBlank
        String title,

        @ValidBookAuthor @NotBlank
        String author,

        @ValidBookISBN @NotBlank
        String isbn,

        @ValidBookDescription
        String description,

        @ValidBookPublishedYear
        @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")
        @NotNull @JsonbTypeDeserializer(DateDeserializer.class)
        String publishedYear,

        @ValidateBookPages @Positive @NotNull
        String pages) {
}
