package unicam.ids.HackHub.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unicam.ids.HackHub.dto.requests.payment.VerifyPaymentRequest;
import unicam.ids.HackHub.dto.responses.PaymentStatusResponse;
import unicam.ids.HackHub.service.HackathonService;
import unicam.ids.HackHub.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
@Tag(name = "Pagamenti")
public class PaymentController {

    @Autowired
    private HackathonService hackathonService;

    @GetMapping("/organizzatore/status")
    public ResponseEntity<PaymentStatusResponse> verifyPricePayment(@RequestBody @Valid VerifyPaymentRequest verifyPaymentRequest) {
        return ResponseEntity.ok(hackathonService.verifyHackathonPricePayment(verifyPaymentRequest.hackathonName()));
    }
}
