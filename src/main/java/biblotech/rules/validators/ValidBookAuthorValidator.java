package biblotech.rules.validators;

import biblotech.rules.ValidBookAuthor;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static biblotech.rules.util.UtilValidatorHelpers.isFirstLetterUpperCaseChecker;


public class ValidBookAuthorValidator implements ConstraintValidator<ValidBookAuthor, String> {
    @Override
    public boolean isValid(String bookAuthor, ConstraintValidatorContext constraintValidatorContext) {

        if (bookAuthor == null) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Book Author cannot be blank or null").addPropertyNode("author").addConstraintViolation();
            return false;
        }

        if(bookAuthor.trim().isEmpty()){
            constraintValidatorContext.buildConstraintViolationWithTemplate("Book Author cannot be empty").addPropertyNode("author").addConstraintViolation();
        }

        if(isFirstLetterUpperCaseChecker(bookAuthor)) {
            return true;
        }
        constraintValidatorContext.buildConstraintViolationWithTemplate("Book Author is not valid format first letter must be uppercase").addPropertyNode("author").addConstraintViolation();
        return false;
    }


}
