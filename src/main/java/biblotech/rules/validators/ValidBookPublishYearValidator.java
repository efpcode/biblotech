package biblotech.rules.validators;

import biblotech.rules.ValidBookPublishedYear;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ValidBookPublishYearValidator implements ConstraintValidator<ValidBookPublishedYear, String> {
    static LocalDate minimumDate = LocalDate.of(1440, 1, 1);
    static LocalDate maximumDate = LocalDate.now();
    static String format = "yyyy-MM-dd";
    @Override
    public boolean isValid(String year, ConstraintValidatorContext context) {
        if (year == null || year.trim().isEmpty()) {
            context.buildConstraintViolationWithTemplate("Please provide a valid publish year: null or empty is not valid").addConstraintViolation();
            return false;
        }

        try {
            LocalDate yearValue = LocalDate.parse(year.trim());
            if (yearValue.isBefore(minimumDate) || yearValue.isAfter(maximumDate)) {
                context.buildConstraintViolationWithTemplate("Please provide a valid publish year: invalid date range from: "+minimumDate+" to " + maximumDate ).addConstraintViolation();
                return false;
            }
        } catch (DateTimeParseException e) {
            context.buildConstraintViolationWithTemplate("Please provide a valid publish year: invalid date format").addConstraintViolation();
            return false;
        }

        return true;
    }
}
