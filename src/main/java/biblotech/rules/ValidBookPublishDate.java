package biblotech.rules;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValidBookPublishDateValidator.class})
public @interface ValidBookPublishDate {
    String message() default "Publication Date is invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
