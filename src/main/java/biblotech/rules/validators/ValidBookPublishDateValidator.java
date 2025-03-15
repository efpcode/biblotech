package biblotech.rules.validators;

import biblotech.rules.ValidBookPublishDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class ValidBookPublishDateValidator implements ConstraintValidator<ValidBookPublishDate, LocalDate> {
    /*
    MiniumDate based on the year of Guttenberg press was invented Year 1440 and that started the modern printing of books.
     */
    static LocalDate minimumDate = LocalDate.of(1440, 1, 1);
    static LocalDate maximumDate = LocalDate.now();
    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {

        if(localDate == null) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Publication Date cannot be null").addPropertyNode("bookPublishDate").addConstraintViolation();
            return false;
        }


        if (!(localDate.isBefore(minimumDate) || localDate.isAfter(maximumDate))) {
            return true;
        }

        constraintValidatorContext.buildConstraintViolationWithTemplate("Publication Date is not within the range of valid date 1450 to present").addPropertyNode("bookPublishDate").addConstraintViolation();



        return false;
    }



}
