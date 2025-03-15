package biblotech.rules.validators;

import biblotech.rules.ValidBookISBN;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class ValidBookISBNValidator implements ConstraintValidator<ValidBookISBN, String> {

    @Override
    public boolean isValid(String isbnDigits, ConstraintValidatorContext constraintValidatorContext) {
        if(isbnDigits == null || isbnDigits.isEmpty()){
            constraintValidatorContext.buildConstraintViolationWithTemplate("ISBN number cannot be empty").addPropertyNode("isbn").addConstraintViolation();
        }

        if(!(isValidLengthOfISBN(isbnDigits))){
            constraintValidatorContext.buildConstraintViolationWithTemplate("ISBN is not of valid length 10 or 13 digits").addPropertyNode("isbn").addConstraintViolation();
            return false;
        }

        if(isValidISBN10(isbnDigits) || isValidISBN13(isbnDigits)){
            return true;
        }
        constraintValidatorContext.buildConstraintViolationWithTemplate("ISBN does not pass digit check").addPropertyNode("isbn").addConstraintViolation();
        return false;
    }


    private boolean isValidLengthOfISBN(String isbnDigits) {
        if(getValidDigitsForISBN10(isbnDigits).size()==10 || getValidDigitsForISBN10(isbnDigits).size()==13){
            return true;
        }
        return false;
    }

    private boolean isValidISBN10(String isbnDigits) {
        var onlyDigits = getValidDigitsForISBN10(isbnDigits);

        int sum = 0;
        int i;
        int product;

        for (i=0; i <= onlyDigits.size()-2; i++){
            int digit = Integer.parseInt(onlyDigits.get(i));
            product = digit * (onlyDigits.size() -i);
            sum += product;
        }
        sum = onlyDigits.getLast().equals("X") ? sum + 10 : sum + Integer.parseInt(onlyDigits.getLast());


        return sum % 11 ==0;

    }

    private boolean isValidISBN13(String isbnDigits) {
        var onlyDigits = getValidDigitsForISBN13(isbnDigits);
        int sum = 0;
        int product;
        int i;
        for (i=0; i <= onlyDigits.size()-1; i++){
            int digit = Integer.parseInt(onlyDigits.get(i));
            product = (i % 2==0) ? digit * 1: digit * 3;
            sum += product;
        }
        return sum % 10 ==0;

    }

    private List<String> getValidDigitsForISBN13(String isbnDigits) {
        return Arrays.stream(isbnDigits.split(""))
                .filter(s -> s.matches("\\d"))
                .toList();
    }

    private static List<String> getValidDigitsForISBN10(String isbnDigits) {
        return Arrays.stream(isbnDigits.split(""))
                .filter(s -> s.matches("\\d|X"))
                .toList();
    }

}
