package biblotech.mapper;

import java.time.LocalDate;

public class YearMapper {
    private YearMapper() {}

    public static LocalDate getYearFromString(String year) {
        return LocalDate.parse(year);
    }
}
