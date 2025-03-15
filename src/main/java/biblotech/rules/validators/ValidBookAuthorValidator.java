package biblotech.rules.validators;

import biblotech.rules.ValidBookAuthor;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class ValidBookAuthorValidator implements ConstraintValidator<ValidBookAuthor, String> {
    @Override
    public boolean isValid(String bookAuthor, ConstraintValidatorContext constraintValidatorContext) {

        if (bookAuthor.isBlank()) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Book Author cannot be blank or null").addPropertyNode("author").addConstraintViolation();
            return false;
        }

        if(isAuthorValid(bookAuthor)) {
            return true;
        }
        constraintValidatorContext.buildConstraintViolationWithTemplate("Book Author is not valid format first letter must be uppercase").addPropertyNode("author").addConstraintViolation();
        return false;
    }

    private boolean isAuthorValid(String authorName){
        return Character.isUpperCase(authorName.charAt(0));

    }
}
