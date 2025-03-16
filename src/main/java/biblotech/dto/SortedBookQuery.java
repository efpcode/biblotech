package biblotech.dto;


import biblotech.rules.ValidSortedBookQuery;
import jakarta.validation.constraints.NotBlank;

public record SortedBookQuery(@NotBlank @ValidSortedBookQuery String sortBy){}
