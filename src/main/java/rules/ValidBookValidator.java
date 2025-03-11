package rules;

import dto.CreateBook;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidBookValidator  implements ConstraintValidator<ValidBookTitle, CreateBook> {
    @Override
    public boolean isValid(CreateBook createBook, ConstraintValidatorContext constraintValidatorContext) {
        if (createBook.title() == null || createBook.title().isBlank()) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Title cannot be empty").addPropertyNode("title").addConstraintViolation();
            return false;
        }
        if(isUpperCase(createBook.title().charAt(0))){
            return true;
        }
        constraintValidatorContext.buildConstraintViolationWithTemplate("Title is not valid first letter must be uppercase").addPropertyNode("title").addConstraintViolation();
        return false;
    }


    private boolean isUpperCase(int codePoint){
        return Character.isUpperCase(codePoint);
    }
}
