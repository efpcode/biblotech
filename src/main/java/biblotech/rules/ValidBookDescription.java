package biblotech.rules;

import biblotech.rules.validators.ValidBookDescriptionValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValidBookDescriptionValidator.class})
public @interface ValidBookDescription {
    String message() default "Invalid book description";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
