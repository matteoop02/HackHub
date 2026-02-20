package unicam.ids.HackHub.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import unicam.ids.HackHub.dto.requests.hackathon.CreateHackathonRequest;

import java.time.LocalDateTime;

public class ChronologicalDatesValidator implements ConstraintValidator<ChronologicalDates, CreateHackathonRequest> {

    @Override
    public boolean isValid(CreateHackathonRequest request, ConstraintValidatorContext context) {
        LocalDateTime subscription = request.subscriptionDeadline();
        LocalDateTime start = request.startDate();
        LocalDateTime end = request.endDate();

        boolean valid = subscription.isBefore(start) && start.isBefore(end);

        if (!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Le date devono rispettare: subscriptionDeadline < startDate < endDate"
            ).addConstraintViolation();
        }

        return valid;
    }
}
