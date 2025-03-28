package biblotech.rules;

import biblotech.rules.validators.ValidBookTitleValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValidBookTitleValidator.class})
public @interface ValidBookTitle {
    String message() default "Not a valid book title";
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default {};

}
