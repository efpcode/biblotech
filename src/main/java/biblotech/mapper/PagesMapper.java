package biblotech.mapper;

import biblotech.exceptions.InvalidBookPage;

public class PagesMapper {

    private  PagesMapper() {}

    public static Long mapToLong(String pages) {

        if (pages == null || pages.trim().isEmpty()) {
            return 0L;
        }else {

            try {
                return Long.valueOf(pages);
            }catch (NumberFormatException e) {
                throw new InvalidBookPage("Invalid page(s) input, please provide a valid page number");
            }
        }



    }
}
