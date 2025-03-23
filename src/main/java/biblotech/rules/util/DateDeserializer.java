package biblotech.rules.util;

import biblotech.exceptions.InvaildDateFormat;
import jakarta.json.bind.JsonbException;
import jakarta.json.bind.serializer.DeserializationContext;
import jakarta.json.bind.serializer.JsonbDeserializer;
import jakarta.json.stream.JsonParser;
import jakarta.ws.rs.ext.Provider;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Provider
public class DateDeserializer implements JsonbDeserializer <String> {
    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, Type type) {
        String date = jsonParser.getString();
        try {
            if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                return date;
            }
            var dateCheck =  LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return dateCheck.format(DateTimeFormatter.ISO_DATE);
        } catch (DateTimeParseException e) {

            throw new InvaildDateFormat("Invalid date format: " + date + ". Please check your date format and make sure that date has quotation marks around it and follows the standard yyyy-MM-dd.");
        }
    }
}
