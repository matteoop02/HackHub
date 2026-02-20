package unicam.ids.HackHub.dto.requests.payment;

import jakarta.validation.constraints.NotEmpty;

public record VerifyPaymentRequest (

    @NotEmpty(message = "Il nome dell'hackathon non può essere vuoto")
    String hackathonName
) {}
