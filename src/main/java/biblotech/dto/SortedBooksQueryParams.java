package biblotech.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SortedBooksQueryParams(
        @Positive Long pageNumber,
        @Positive Integer pageSize,
        @NotNull String sortBy,
        @NotNull String sortOrder) {

 }
