package unicam.ids.HackHub.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ChronologicalDatesValidator.class)
@Documented
public @interface ChronologicalDates {
    String message() default "Le date non sono coerenti";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
