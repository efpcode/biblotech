package biblotech.mapper;

import biblotech.exceptions.InvaildDateFormat;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class YearMapper {
    private YearMapper() {}

    public static LocalDate getYearFromString(String year) {
        if(year == null) {
            return null;
        }


        try {
            return LocalDate.parse(year);
        }catch (DateTimeParseException e) {
            throw new InvaildDateFormat("Year could not be parsed: " + year);
        }
    }
}
