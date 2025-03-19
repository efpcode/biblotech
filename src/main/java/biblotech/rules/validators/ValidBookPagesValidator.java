package biblotech.rules.validators;

import biblotech.rules.ValidateBookPages;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class ValidBookPagesValidator implements ConstraintValidator<ValidateBookPages, Long> {

    @Override
    public boolean isValid(Long pages , ConstraintValidatorContext constraintValidatorContext) {



        if (pages == null) {
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate("Pages cannot be null")
                    .addPropertyNode("pages")
                    .addConstraintViolation();
            return false;
        }

        if (pages <= 0) {
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate("Pages must be a positive value")
                    .addPropertyNode("pages")
                    .addConstraintViolation();
            return false;
        }

        try {
            var test =Long.parseLong(String.valueOf(pages));

        }catch(NumberFormatException e) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Pages must be a number").addPropertyNode("pages").addConstraintViolation();
            return false;
        }
        return true;
    }
}
