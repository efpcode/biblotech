package biblotech.rules.validators;

import biblotech.rules.ValidBookISBN;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static biblotech.rules.util.UtilValidatorHelpers.*;

public class ValidBookISBNValidator implements ConstraintValidator<ValidBookISBN, String> {

    @Override
    public boolean isValid(String isbnDigits, ConstraintValidatorContext constraintValidatorContext) {
        if(isbnDigits == null || isbnDigits.isEmpty()){
            constraintValidatorContext.buildConstraintViolationWithTemplate("ISBN number cannot be empty").addPropertyNode("isbn").addConstraintViolation();
            return false;
        }

        if(!(isValidLengthOfISBN(isbnDigits))){
            constraintValidatorContext.buildConstraintViolationWithTemplate("ISBN is not of valid length 10 or 13 digits").addPropertyNode("isbn").addConstraintViolation();
            return false;
        }

        if(isValidISBN10(isbnDigits) || isValidISBN13(isbnDigits)){
            return true;
        }
        constraintValidatorContext.buildConstraintViolationWithTemplate("ISBN does not pass digit check").addPropertyNode("isbn").addConstraintViolation();
        return false;
    }

}
