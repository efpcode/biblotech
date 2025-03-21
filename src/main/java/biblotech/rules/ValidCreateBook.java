package biblotech.rules;

import biblotech.rules.validators.ValidCreateBookValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidCreateBookValidator.class)
public @interface ValidCreateBook {
    String message() default "Not a valid createBook";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
