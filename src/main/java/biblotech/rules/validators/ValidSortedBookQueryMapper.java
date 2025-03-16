package biblotech.rules.validators;

import biblotech.rules.ValidSortedBookQuery;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class ValidSortedBookQueryMapper implements ConstraintValidator<ValidSortedBookQuery, String> {
    private final String[] SORT_BY_FIELDS = { "title", "author", "isbn", "description", "pages", "published"};
    @Override
    public boolean isValid(String sortBy, ConstraintValidatorContext constraintValidatorContext) {
        if (sortBy == null) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("sort by field is null").addConstraintViolation();
            return false;
        }

        if(Arrays.stream(SORT_BY_FIELDS).toList().contains(sortBy)) {
            return true;
        }

        constraintValidatorContext
                .buildConstraintViolationWithTemplate("sort by field is not valid\nExpected values are: " + Arrays.toString(SORT_BY_FIELDS))
                .addConstraintViolation();


        return false;
    }
}
