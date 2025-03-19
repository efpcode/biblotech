package biblotech.rules.validators;

import biblotech.rules.ValidBookDescription;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidBookDescriptionValidator implements ConstraintValidator<ValidBookDescription, String> {
    @Override
    public boolean isValid(String info, ConstraintValidatorContext context) {
       if (info == null || info.trim().isEmpty()) {
           context.buildConstraintViolationWithTemplate("Book description is empty").addConstraintViolation();
           return false;
       }

       if (info.isEmpty()) {
           context.buildConstraintViolationWithTemplate("Book description cannot be empty").addConstraintViolation();
           return false;
       }

       if  (info.length() > 1000) {
           context.buildConstraintViolationWithTemplate("Book description length exceeds 1000 characters").addConstraintViolation();
           return false;
       }

        return true;
    }
}
