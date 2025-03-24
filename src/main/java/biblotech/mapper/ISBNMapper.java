package biblotech.mapper;

public class ISBNMapper {
    private ISBNMapper(){}

    public static String mapToBook(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            return null;
        }

        return isbn.replaceAll("[^0-9|Xx]", "").toUpperCase();
    }


}
