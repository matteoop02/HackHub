package unicam.ids.HackHub.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import unicam.ids.HackHub.dto.requests.CreateTeamSubmissionRequest;
import unicam.ids.HackHub.dto.requests.HackathonSubmissionEvaluationRequest;
import unicam.ids.HackHub.dto.requests.UpdateTeamSubmissionRequest;
import unicam.ids.HackHub.model.Submission;
import unicam.ids.HackHub.service.SubmissionService;
import java.util.List;

@RestController
@RequestMapping("/api/submission")
@Tag(name = "Sottomissioni", description = "Gestione delle sottomissioni")
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;

    @PostMapping("/staff/listByHackathon")
    public ResponseEntity<List<Submission>> getSubmissionsListByHackathonName(@RequestParam @Valid String hackathonName) {
        try {
            List<Submission> submissions = submissionService.getSubmissionsByHackathonName(hackathonName);
            return ResponseEntity.ok(submissions);
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/staff/listByTeam")
    public ResponseEntity<List<Submission>> getSubmissionsListByTeamName(@RequestParam @Valid String teamName) {
        try {
            List<Submission> submissions = submissionService.getSubmissionsByTeamName(teamName);
            return ResponseEntity.ok(submissions);
        }
        catch(Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/team/create")
    public ResponseEntity<String> createSubmission(Authentication authentication, @RequestBody @Valid CreateTeamSubmissionRequest createTeamSubmissionRequest) {
        try {
            submissionService.createSubmission(authentication, createTeamSubmissionRequest);
            return ResponseEntity.ok("Sottomissione creata");
        }
        catch(Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/team/update")
    public ResponseEntity<String> updateSubmission(@RequestBody @Valid UpdateTeamSubmissionRequest updateTeamSubmissionRequest) {
        try {
            submissionService.updateSubmission(updateTeamSubmissionRequest);
            return ResponseEntity.ok("Sottomissione modificata");
        }
        catch(Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/judge/evaluateHackathonSubmission")
    public ResponseEntity<String> evaluateHackathonSubmission(@RequestBody HackathonSubmissionEvaluationRequest hackathonSubmissionEvaluationRequest) {
        try {
            submissionService.evaluateHackathonSubmission(hackathonSubmissionEvaluationRequest);
            return ResponseEntity.ok("Valutazione completata");
        }
        catch(Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}
