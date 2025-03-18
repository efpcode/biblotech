package biblotech.mapper;

import biblotech.exceptions.InvalidSortByQueryException;

import java.util.Arrays;

public class SortedBookQueryMapper {

    private  SortedBookQueryMapper() {

    }

    static final String[] SORT_BY_FIELDS = { "title", "author", "isbn", "description", "pages", "published"};
    public static String mapToSortedBookQuery(String sortedBookQuery) {

        String sortBy = sortedBookQuery.toLowerCase();

        if (!Arrays.asList(SORT_BY_FIELDS).contains(sortBy)) {
            throw new InvalidSortByQueryException("Invalid sort by: " + sortBy + ".\n Valid values are: " + Arrays.toString(SORT_BY_FIELDS));
        }

        return switch (sortBy){
            case "title" -> "bookTitle";
            case "author" -> "bookAuthor";
            case "isbn" -> "bookIsbn";
            case "description" -> "bookDescription";
            case "pages" -> "bookPagesNumber";
            case "published" -> "bookPublishDate";
            default -> sortBy;
        };




    }


}
