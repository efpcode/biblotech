package biblotech.rules.validators;

import biblotech.rules.ValidateBookPages;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidBookPagesStringValidator implements ConstraintValidator<ValidateBookPages, String> {
    @Override
    public boolean isValid(String pages, ConstraintValidatorContext constraintValidatorContext) {

        if (pages == null  || pages.trim().isEmpty()) {
            return true;
        }

        try{
           var digit = Long.parseLong(pages);
           if (digit <= 0) {
               constraintValidatorContext.buildConstraintViolationWithTemplate("Pages must be greater then 0. ").addConstraintViolation();
               return false;
           }

        }catch(NumberFormatException e){
            constraintValidatorContext.buildConstraintViolationWithTemplate("Invalid book pages").addPropertyNode("pages").addConstraintViolation();
            return false;
        }
        return true;
    }
}
