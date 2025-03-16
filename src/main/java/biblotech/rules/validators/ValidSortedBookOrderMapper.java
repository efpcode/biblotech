package biblotech.rules.validators;

import biblotech.rules.ValidSortedBookOrder;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class ValidSortedBookOrderMapper implements ConstraintValidator<ValidSortedBookOrder, String> {
    String[] SORT_ORDER_FIELDS = {"asc", "desc"};
    @Override
    public boolean isValid(String sortOrder, ConstraintValidatorContext constraintValidatorContext) {

        if (sortOrder == null) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("sort order is null").addConstraintViolation();
            return false;
        }

        if(Arrays.asList(SORT_ORDER_FIELDS).contains(sortOrder)) {
            return true;
        }
        constraintValidatorContext
                .buildConstraintViolationWithTemplate("sort order is not valid options. Expected values are: " + Arrays.toString(SORT_ORDER_FIELDS))
                .addConstraintViolation();

        return false;
    }
}
