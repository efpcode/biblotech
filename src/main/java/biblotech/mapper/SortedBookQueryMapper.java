package biblotech.mapper;

import biblotech.exceptions.InvalidSortByQueryException;
import org.hibernate.annotations.Immutable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class SortedBookQueryMapper {

    private  SortedBookQueryMapper() {

    }

    private static final Set<String> SORT_BY_FIELDS = Set.of( "title", "author", "isbn", "description", "pages", "published");
    public static String mapToSortedBookQuery(String sortedBookQuery) {

        String sortBy = sortedBookQuery.trim().toLowerCase();

        if (!SORT_BY_FIELDS.contains(sortBy)) {
            throw new InvalidSortByQueryException("Invalid sort by: " + sortBy + ".\n Valid values are: " + SORT_BY_FIELDS);
        }

        return switch (sortBy){
            case "title" -> "bookTitle";
            case "author" -> "bookAuthor";
            case "isbn" -> "bookIsbn";
            case "description" -> "bookDescription";
            case "pages" -> "pages";
            case "published" -> "bookPublishDate";
            default -> throw new InvalidSortByQueryException("Invalid sort by: " + sortBy);
        };




    }


}
