package unicam.ids.HackHub.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unicam.ids.HackHub.dto.requests.ReportRequest;
import unicam.ids.HackHub.service.ReportService;

@RestController
@RequestMapping("/api/report")
@Tag(name = "Report", description = "Gestione dei report (violazione, ecc...)")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @PostMapping("/mentor/reportRequest")
    public ResponseEntity<String> reportRequest(Authentication authentication, @RequestBody @Valid ReportRequest reportRequest) {
        try {
            reportService.reportRequest(authentication, reportRequest);
            return ResponseEntity.ok("Segnalazione Effettuata");
        }
        catch(Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
