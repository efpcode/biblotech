package biblotech.rules.validators;

import biblotech.rules.ValidBookTitle;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidBookTitleValidator implements ConstraintValidator<ValidBookTitle, String> {
    @Override
    public boolean isValid(String title, ConstraintValidatorContext constraintValidatorContext) {
        if (title == null) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Title cannot be null").addPropertyNode("title").addConstraintViolation();
            return false;
        }

        if (title.isEmpty()) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Title cannot be empty").addPropertyNode("title").addConstraintViolation();
            return false;
        }

        if(!(isUpperCase(title.charAt(0)))){
            constraintValidatorContext.buildConstraintViolationWithTemplate("Title is not valid first letter must be uppercase").addPropertyNode("title").addConstraintViolation();
            return false;
        }

        if(isTitleValidFormatted(title)){
            return true;
        }

        constraintValidatorContext.buildConstraintViolationWithTemplate("Title is not a valid format").addPropertyNode("title").addConstraintViolation();

        return false;
    }


    private boolean isUpperCase(int codePoint){
        return Character.isUpperCase(codePoint);
    }

    private boolean isTitleValidFormatted(String title){
        if(title.trim().isEmpty()){
            return false;
        }

        String regex = "^[A-Za-z0-9]+(?:[\\s][A-Za-z0-9]+)*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(title);
        return matcher.matches();
    }
}
