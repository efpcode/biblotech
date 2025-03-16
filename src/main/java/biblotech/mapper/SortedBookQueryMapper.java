package biblotech.mapper;

import biblotech.dto.SortedBookQuery;
import biblotech.exceptions.InvalidSortByQueryException;

import java.util.Arrays;

public class SortedBookQueryMapper {

    private  SortedBookQueryMapper() {

    }
    public static SortedBookQuery mapToSortedBookQuery(SortedBookQuery sortedBookQuery, String[] expectedQuery) {

        String sortBy = sortedBookQuery.sortBy().toLowerCase();

        if (!Arrays.asList(expectedQuery).contains(sortBy)) {
            throw new InvalidSortByQueryException("Invalid sort by: " + sortBy + ".\n Valid values are: " + Arrays.toString(expectedQuery));
        }

        String mapToEntityColumn = switch (sortBy){
            case "title" -> "bookTitle";
            case "author" -> "bookAuthor";
            case "isbn" -> "bookIsbn";
            case "description" -> "bookDescription";
            case "pages" -> "bookPageNumber";
            case "published" -> "bookPublished";
            default -> sortBy;
        };

        return new SortedBookQuery(mapToEntityColumn);



    }


}
