package unicam.ids.HackHub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unicam.ids.HackHub.dto.ComplexDTO.HackathonSubmissionsEvaluationDTO;
import unicam.ids.HackHub.dto.requests.CreateHackathonRequest;
import unicam.ids.HackHub.model.Submission;
import unicam.ids.HackHub.model.User;
import unicam.ids.HackHub.repository.SubmissionRepository;
import unicam.ids.HackHub.repository.UserRepository;
import unicam.ids.HackHub.service.SubmissionService;
import unicam.ids.HackHub.service.UserService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/submission")
public class SubmissionController {
    @Autowired
    private SubmissionRepository submissionRepository;
    @Autowired
    private SubmissionService submissionService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;


    @PostMapping("/listByHackathon")
    public ResponseEntity<List<Submission>> getSubmissionsListByHackathonName(@RequestBody String hackathonName) {
        try {
            List<Submission> submmissions = submissionService.getSubmissionsByHackathonName(hackathonName);
            return ResponseEntity.ok(submmissions);
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/listByTeam")
    public ResponseEntity<List<Submission>> getSubmissionsListByTeamName(@RequestBody String teamName) {
        try {
            List<Submission> submissions = submissionService.getSubmissionsByTeamName(teamName);
            return ResponseEntity.ok(submissions);
        }
        catch(Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateSubmission(@RequestBody String title, String content, Date sendingDate, Date lastEdit) {
        try {
            submissionService.updateSubmission(title, content, sendingDate, lastEdit);
            return ResponseEntity.ok("Sottomissione modificata");
        }
        catch(Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/evaluateHackathonSubmissions")
    public ResponseEntity<String> evaluateHackathonSubmissions(@RequestBody HackathonSubmissionsEvaluationDTO hackathonSubmissionsEvaluationDTO) {
        try {
            //submissionService.evaluateHackathonSubmissions(hackathonSubmissionsEvaluationDTO.getJudgeId(), hackathonSubmissionsEvaluationDTO.getHackathonId(), hackathonSubmissionsEvaluationDTO.getJudgements());
            return ResponseEntity.ok("Valutazione completata");
        }
        catch(Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}
