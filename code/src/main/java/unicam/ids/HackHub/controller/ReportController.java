package unicam.ids.HackHub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unicam.ids.HackHub.dto.ComplexDTO.ViolationReportDTO;
import unicam.ids.HackHub.services.TeamService;
import unicam.ids.HackHub.services.UserService;

@RestController
@RequestMapping("/api/report")
public class ReportController {
    @Autowired
    private UserService userService;

    @PostMapping("/request")
    public ResponseEntity<String> violationReportRequest(@RequestBody ViolationReportDTO violationReportDTO) {
        try {
            userService.violationReportRequest(violationReportDTO.getMentorId(), violationReportDTO.getDescription(), violationReportDTO.getTeamId(), violationReportDTO.getOrganizerId());
            return ResponseEntity.ok("Segnalazione Effettuata");
        }
        catch(Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
