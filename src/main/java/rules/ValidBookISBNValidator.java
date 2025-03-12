package rules;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class ValidBookISBNValidator implements ConstraintValidator<ValidBookISBN, String> {

    @Override
    public boolean isValid(String isbnDigits, ConstraintValidatorContext constraintValidatorContext) {
        if(isbnDigits == null || isbnDigits.isEmpty()){
            constraintValidatorContext.buildConstraintViolationWithTemplate("ISBN number cannot be empty").addPropertyNode("isbn").addConstraintViolation();
        }

        if(isValidLengthOfISBN(isbnDigits)){
            return true;
        }
        constraintValidatorContext.buildConstraintViolationWithTemplate("ISBN is not of valid length 10 or 13 digits").addPropertyNode("isbn").addConstraintViolation();
        return false;
    }


    private boolean isValidLengthOfISBN(String isbnDigits) {
        var regexPattern = Pattern.compile("\\d{10}|\\d{13}");
        var  isbn = regexPattern.matcher(isbnDigits);
        return isbn.matches();
    }

}
