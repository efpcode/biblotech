package biblotech.rules.validators;

import biblotech.dto.PatchBook;
import biblotech.rules.ValidPatchBook;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import static biblotech.rules.util.UtilValidatorHelpers.*;


public class ValidPatchBookValidator implements ConstraintValidator<ValidPatchBook, PatchBook> {
    static LocalDate maxDate =  LocalDate.now();
    static LocalDate minDate =  LocalDate.of(1440, 1, 1);

    @Override
    public boolean isValid(PatchBook patchBook, ConstraintValidatorContext context) {
        boolean isValid  = true;

        if (patchBook == null) {
            isValid = false;
        }

        if (patchBook.getTitle()!= null) {

            isValid = isTitleValid(patchBook, context, isValid);

        }

        if(patchBook.getAuthor()!= null) {

            isValid = isAuthorValid(patchBook, context, isValid);

        }

        if(patchBook.getIsbn()!= null) {

            isValid = isIsbnValid(patchBook, context, isValid);

        }

        if (patchBook.getDescription()!= null) {

            if(patchBook.getDescription().trim().isEmpty()){
                isValid = false;
                context.buildConstraintViolationWithTemplate("Description is empty").addConstraintViolation();

            }

            if(patchBook.getDescription().length() > 1000 ){
                isValid = false;
                context.buildConstraintViolationWithTemplate("Description is either too long over 1000 characters!").addConstraintViolation();
            }
        }

        if  (patchBook.getPublishedYear()!= null) {

            if(patchBook.getPublishedYear().trim().isEmpty()) {
                isValid = false;
                context.buildConstraintViolationWithTemplate("Published Year is empty").addConstraintViolation();
            }

            if(!patchBook.getPublishedYear().matches("^\\d{4}-\\d{2}-\\d{2}$") ) {
                isValid = false;
                context.buildConstraintViolationWithTemplate("Published Year is invalid because of format expected yyyy-MM-dd").addConstraintViolation();
            }



            if( LocalDate.parse(patchBook.getPublishedYear()).isBefore(minDate) || LocalDate.parse(patchBook.getPublishedYear()).isAfter(maxDate) ){
                isValid = false;
                context.buildConstraintViolationWithTemplate("Published is out the valid range minDate: "+ minDate + "and maxDate: " + maxDate).addConstraintViolation();
            }


        }

        if (patchBook.getPages()!= null) {

            if(patchBook.getPages().trim().isEmpty()) {
                isValid = false;
                context.buildConstraintViolationWithTemplate("Pages is empty").addConstraintViolation();

            }

            try{
                var digit = Long.parseLong(patchBook.getPages());
                if(digit <=0 ){
                    isValid = false;
                    context.buildConstraintViolationWithTemplate("Pages must be greater then 0. ").addConstraintViolation();
                }
            }catch(NumberFormatException e){
                isValid = false;
                context.buildConstraintViolationWithTemplate("Pages is not a number").addConstraintViolation();
            }

        }

        return isValid;
    }

    private boolean isIsbnValid(PatchBook patchBook, ConstraintValidatorContext context, boolean isValid) {
        if(patchBook.getIsbn().trim().isEmpty()){
            isValid = false;
            context.buildConstraintViolationWithTemplate("isbn is empty").addConstraintViolation();
        }

        if(patchBook.getIsbn().length() != 10 && patchBook.getIsbn().length() != 13) {
            isValid = false;
            context.buildConstraintViolationWithTemplate("isbn is invalid expected length of 10 or 13").addConstraintViolation();
        }

        if(!isValidISBN10(patchBook.getIsbn()) && !isValidISBN13(patchBook.getIsbn())) {
            isValid = false;
            context.buildConstraintViolationWithTemplate("isbn is does not pass digit verification").addConstraintViolation();

        }
        return isValid;
    }



    private boolean isAuthorValid(PatchBook patchBook, ConstraintValidatorContext context, boolean isValid) {
        if(patchBook.getAuthor().trim().isEmpty()) {
            isValid = false;
            context.buildConstraintViolationWithTemplate("Author cannot be empty").addConstraintViolation();

        }
        if(!isFirstLetterUpperCaseChecker(patchBook.getAuthor())) {
            isValid = false;
            context.buildConstraintViolationWithTemplate("Author name first character must be uppercase").addConstraintViolation();
        }
        return isValid;
    }

    private boolean isTitleValid(PatchBook patchBook, ConstraintValidatorContext context, boolean isValid) {
        if(patchBook.getTitle().trim().isEmpty()) {
            isValid = false;
            context.buildConstraintViolationWithTemplate("Title cannot be empty").addConstraintViolation();
        }

        if(!isFirstLetterUpperCaseChecker(patchBook.getTitle())) {
            isValid = false;
            context.buildConstraintViolationWithTemplate("First letter of title must be uppercase").addConstraintViolation();

        }
        if(!isTitleValidChecker(patchBook.getTitle())) {
            isValid = false;
            context.buildConstraintViolationWithTemplate("Title invalid contains unwanted characters").addConstraintViolation();
        }
        return isValid;
    }




}
