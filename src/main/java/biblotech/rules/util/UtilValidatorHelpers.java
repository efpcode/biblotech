package biblotech.rules.util;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilValidatorHelpers {

    private UtilValidatorHelpers() {}

    public static boolean isFirstLetterUpperCaseChecker(String target) {
        if (target.trim().isEmpty()) {
            return false;
        }
        try{
            Long.parseLong(target.trim().substring(0, 1));
            return true;
        }catch (NumberFormatException e){
            return  Character.isUpperCase(target.charAt(0));
        }

    }

    public static boolean isTitleValidChecker(String target) {

        String regex = "^[A-Za-z0-9\\s'\"-:;,.!?()&]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(target);
        return matcher.matches();
    }


    public static boolean isValidISBN10(String isbnDigits) {
        var onlyDigits = getValidDigitsForISBN10(isbnDigits);
        var constant = 10;

        int sum = 0;

        for (int i=0; i < 9; i++){
            int digit = Integer.parseInt(onlyDigits.get(i));
            sum += digit * (constant -i);
        }
        sum = onlyDigits.get(9).equals("X") ? sum + 10 : sum + Integer.parseInt(onlyDigits.get(9));


        return sum % 11 == 0;

    }

    public static boolean isValidISBN13(String isbnDigits) {
        var digits = getValidDigitsForISBN13(isbnDigits);
        int sum = 0;
        for (int i=0; i <=  digits.size()-1; i++){
            int digit = Integer.parseInt( digits.get(i));
            sum += (i % 2==0) ? digit: digit * 3;
        }
        return sum % 10 ==0;

    }

    public static List<String> getValidDigitsForISBN13(String isbnDigits) {
        return Arrays.stream(isbnDigits.split(""))
                .filter(s -> s.matches("\\d"))
                .toList();
    }

    public static List<String> getValidDigitsForISBN10(String isbnDigits) {
        return Arrays.stream(isbnDigits.split(""))
                .filter(s -> s.matches("\\d|X"))
                .toList();
    }


    public static boolean isValidLengthOfISBN(String isbnDigits) {
        return getValidDigitsForISBN10(isbnDigits).size() == 10 || getValidDigitsForISBN10(isbnDigits).size() == 13;
    }


    public static boolean isTitleValidFormatted(String title){
        if(title.trim().isEmpty()){
            return false;
        }

        String regex = "^[A-Za-z0-9\\s'\"-:;,.!?()&]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(title);
        return matcher.matches();
    }


}
