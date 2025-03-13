package biblotech.rules;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidBookValidator  implements ConstraintValidator<ValidBookTitle, String> {
    @Override
    public boolean isValid(String title, ConstraintValidatorContext constraintValidatorContext) {
        if (title == null || title.isBlank()) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Title cannot be empty").addPropertyNode("title").addConstraintViolation();
            return false;
        }
        if(isUpperCase(title.charAt(0))){
            return true;
        }
        constraintValidatorContext.buildConstraintViolationWithTemplate("Title is not valid first letter must be uppercase").addPropertyNode("title").addConstraintViolation();
        return false;
    }


    private boolean isUpperCase(int codePoint){
        return Character.isUpperCase(codePoint);
    }
}
