package biblotech.rules.validators;

import biblotech.rules.ValidBookTitle;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static biblotech.rules.util.UtilValidatorHelpers.isFirstLetterUpperCaseChecker;
import static biblotech.rules.util.UtilValidatorHelpers.isTitleValidFormatted;

public class ValidBookTitleValidator implements ConstraintValidator<ValidBookTitle, String> {
    @Override
    public boolean isValid(String title, ConstraintValidatorContext constraintValidatorContext) {
        if (title == null) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Title cannot be null").addPropertyNode("title").addConstraintViolation();
            return false;
        }

        if (title.trim().isEmpty()) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Title cannot be empty").addPropertyNode("title").addConstraintViolation();
            return false;
        }

        if (!(isFirstLetterUpperCaseChecker(title))) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Title is not valid first letter must be uppercase").addPropertyNode("title").addConstraintViolation();
            return false;
        }

        if (isTitleValidFormatted(title)) {
            return true;
        }

        constraintValidatorContext.buildConstraintViolationWithTemplate("Title is not a valid format").addPropertyNode("title").addConstraintViolation();

        return false;
    }
}
