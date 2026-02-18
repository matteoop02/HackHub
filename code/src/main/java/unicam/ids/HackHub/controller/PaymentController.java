package unicam.ids.HackHub.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unicam.ids.HackHub.dto.responses.PaymentStatusResponse;
import unicam.ids.HackHub.service.HackathonService;
import unicam.ids.HackHub.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
@Tag(name = "Pagamenti")
public class PaymentController {

    private final PaymentService paymentService;
    private final HackathonService hackathonService;

    public PaymentController(PaymentService paymentService, HackathonService hackathonService) {
        this.paymentService = paymentService;
        this.hackathonService = hackathonService;
    }

    @GetMapping("/organizzatore/status")
    public ResponseEntity<PaymentStatusResponse> verifyPricePayment(@RequestParam String hackathonName) {
        return ResponseEntity.ok(hackathonService.verifyHackathonPricePayment(hackathonName));
    }
}
