package biblotech.rules;

import biblotech.rules.validators.ValidPatchBookValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidPatchBookValidator.class)
public @interface ValidPatchBook {
    String message() default "Not a valid patch book";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
