package unicam.ids.HackHub.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import unicam.ids.HackHub.dto.requests.submission.*;
import unicam.ids.HackHub.model.Submission;
import unicam.ids.HackHub.service.SubmissionService;
import java.util.List;

@RestController
@RequestMapping("/api/submission")
@Tag(name = "Sottomissioni", description = "Gestione delle sottomissioni")
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;

    @GetMapping("/staff/listByStaffMember")
    public ResponseEntity<List<Submission>> getSubmissionsListByHackathonName(Authentication authentication) {
        try {
            List<Submission> submissions = submissionService.getSubmissionsByStaffMember(authentication.getName());
            return ResponseEntity.ok(submissions);
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/staff/listByHackathon")
    public ResponseEntity<List<Submission>> getSubmissionsListByHackathonName(@RequestBody @Valid SubmissionsListByHackathonRequest submissionsListByHackathonRequest) {
        try {
            List<Submission> submissions = submissionService.getSubmissionsByHackathonName(submissionsListByHackathonRequest.hackathonName());
            return ResponseEntity.ok(submissions);
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/staff/listByTeam")
    public ResponseEntity<List<Submission>> getSubmissionsListByTeamName(@RequestBody @Valid SubmissionsListByTeamRequest submissionsListByTeamRequest) {
        try {
            List<Submission> submissions = submissionService.getSubmissionsByTeamName(submissionsListByTeamRequest.teamName());
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

    @PostMapping("/judge/updateEvaluation")
public ResponseEntity<String> updateEvaluation(@RequestBody @Valid HackathonSubmissionEvaluationRequest req) {
    try {
        submissionService.updateHackathonSubmissionEvaluation(req);
        return ResponseEntity.ok("Valutazione aggiornata");
    } catch (Exception ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}

    @GetMapping("/staff/assigned")
public ResponseEntity<List<Submission>> getAssignedSubmissions(Authentication authentication) {
    try {
        return ResponseEntity.ok(
            submissionService.getSubmissionsByStaffMember(authentication.getName())
        );
    } catch (Exception ex) {
        return ResponseEntity.badRequest().build();
    }
}
}
