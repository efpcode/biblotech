package biblotech.rules.validators;

import biblotech.dto.BookFilterQueryResponse;
import biblotech.rules.ValidDateRange;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class ValidDateRangeValidator implements ConstraintValidator<ValidDateRange, BookFilterQueryResponse> {
    private static final LocalDate NOT_BEFORE_DATE = LocalDate.of(1440, 1, 1);
    private static final LocalDate NOT_AFTER_DATE = LocalDate.now();

    @Override
    public boolean isValid(BookFilterQueryResponse searchFilter, ConstraintValidatorContext constraintValidatorContext) {

        if (searchFilter.getStartDate() == null && searchFilter.getEndDate() == null) {
            // If both dates are null, skip the validation checks for date range
            return true;
        }

        var startDate = LocalDate.parse(searchFilter.getStartDate());
        var endDate = LocalDate.parse(searchFilter.getEndDate());


        if((endDate.isEqual(startDate))){
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate("Date range is invalid " + "endDate: "+ endDate + " == "+ "startDate: " +startDate)
                    .addConstraintViolation();
            return false;
        }

        if((startDate.isAfter(endDate))){

            constraintValidatorContext
                    .buildConstraintViolationWithTemplate("End date : "+ endDate + " cannot be earlier than " + "startDate: "+ startDate)
                    .addConstraintViolation();

            return false;
        }

        if((startDate.isBefore(NOT_BEFORE_DATE))){
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate("Start date: "+ startDate + " is before minimum date: "+ NOT_BEFORE_DATE)
                    .addConstraintViolation();
            return false;
        }

        if((endDate.isAfter(NOT_AFTER_DATE))){
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate("End date: "+ endDate + " is after date: "+ NOT_AFTER_DATE)
                    .addConstraintViolation();
            return false;
        }




        if(startDate.isBefore(endDate)) {
            return  true;
        }

        constraintValidatorContext.buildConstraintViolationWithTemplate("Dates entered: are outside range: "+ NOT_BEFORE_DATE+" to "+ NOT_AFTER_DATE).addConstraintViolation();



        return false;
    }
}
