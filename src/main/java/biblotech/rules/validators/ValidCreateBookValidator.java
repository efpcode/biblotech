package biblotech.rules.validators;

import biblotech.dto.CreateBook;
import biblotech.rules.ValidCreateBook;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidCreateBookValidator implements ConstraintValidator<ValidCreateBook, CreateBook> {
    @Override
    public boolean isValid(CreateBook createBook, ConstraintValidatorContext context) {
        var isValid = true;
        if (createBook == null) {
            context.buildConstraintViolationWithTemplate("CreateBook cannot be null").addConstraintViolation();
            isValid = false;

        }
        return isValid;
    }
}
