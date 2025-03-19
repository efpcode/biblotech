package biblotech.rules;

import biblotech.rules.validators.ValidBookPagesStringValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER,  ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValidBookPagesStringValidator.class})
public @interface ValidateBookPages {
    String message() default "Invalid book pages";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
