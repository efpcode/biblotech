package biblotech.rules.validators;

import biblotech.dto.PatchBook;
import biblotech.rules.ValidPatchBook;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidPatchBookValidator implements ConstraintValidator<ValidPatchBook, PatchBook> {

    @Override
    public boolean isValid(PatchBook patchBook, ConstraintValidatorContext context) {
        boolean isValid  = true;

        if (patchBook == null) {
            isValid = false;
        }

        if (patchBook.title() != null) {

            if(patchBook.title().trim().isEmpty()) {
                isValid = false;
                context.buildConstraintViolationWithTemplate("Title cannot be empty").addConstraintViolation();
            }

            if(!isFirstLetterUppercase(patchBook.title())) {
                isValid = false;
                context.buildConstraintViolationWithTemplate("First letter of title must be uppercase").addConstraintViolation();

            }
            if(!isTitleValid(patchBook.title())) {
                isValid = false;
                context.buildConstraintViolationWithTemplate("Title invalid contains unwanted characters").addConstraintViolation();
            }

        }


        return isValid;
    }



    private boolean isFirstLetterUppercase (String target) {
        if (target.trim().isEmpty()) {
            return false;
        }
        return  Character.isUpperCase(target.charAt(0));

    };

    private boolean isTitleValid(String target) {

        String regex = "^[A-Za-z0-9\\s'\"-:;,.!?()&]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(target);
        return matcher.matches();
    }

}
