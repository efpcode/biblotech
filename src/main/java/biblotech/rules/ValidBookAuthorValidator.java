package biblotech.rules;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        constraintValidatorContext.buildConstraintViolationWithTemplate("Book Author is not valid format").addPropertyNode("author").addConstraintViolation();
        return false;
    }

    private boolean isAuthorValid(String authorName){
        String regexPattern = "^([A-Z][a-z]*\\.)?\\s?[A-Z][a-z]+$";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(authorName);
        return matcher.matches();

    }
}
