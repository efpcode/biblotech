package biblotech.rules.validators;

import biblotech.dto.CreateBook;
import biblotech.rules.ValidCreateBook;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidCreateBookValidator implements ConstraintValidator<ValidCreateBook, CreateBook> {
    @Override
    public boolean isValid(CreateBook createBook, ConstraintValidatorContext context) {
        var isValid = true;
        if (createBook == null) {
            isValid = false;
            context.buildConstraintViolationWithTemplate("CreateBook cannot be null").addConstraintViolation();

        }

        if (allFieldsEmpty(createBook)) {
            isValid = false;
            context.buildConstraintViolationWithTemplate("All fields are required").addConstraintViolation();
        }

        return isValid;
    }


    private boolean allFieldsEmpty(CreateBook createBook){

        return createBook.author() == null
                && createBook.title() == null
                && createBook.isbn() == null
                && createBook.description() == null
                && createBook.publishedYear() == null
                && createBook.pages() == null;
    }
}
