package biblotech.rules;

import biblotech.rules.validators.ValidSortedBookOrderMapper;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValidSortedBookOrderMapper.class})
public @interface ValidSortedBookOrder {
    String message() default "sort by order field is not of valid type";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
