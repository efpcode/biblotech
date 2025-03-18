package biblotech.dto;

import biblotech.rules.ValidSortedBookOrder;
import biblotech.rules.ValidSortedBookQuery;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SortedBooksQueryParams(
        @Positive Long pageNumber,
        @Positive Integer pageSize,
        @NotNull @ValidSortedBookQuery String sortBy,
        @NotNull @ValidSortedBookOrder String sortOrder) {

 }
