package biblotech.rules;

import biblotech.rules.validators.ValidSortedBookQueryMapper;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValidSortedBookQueryMapper.class})
public @interface ValidSortedBookQuery {
    String message() default "Query is passed is not a valid sort type for books";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
