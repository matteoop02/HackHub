package unicam.ids.HackHub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unicam.ids.HackHub.dto.ComplexDTO.UserHackathonDTO;
import unicam.ids.HackHub.dto.ComplexDTO.UserTeamDTO;
import unicam.ids.HackHub.dto.HackathonDTO;
import unicam.ids.HackHub.dto.SubmissionDTO;
import unicam.ids.HackHub.dto.UserDTO;
import unicam.ids.HackHub.enums.SubmissionState;
import unicam.ids.HackHub.model.Submission;
import unicam.ids.HackHub.model.User;
import unicam.ids.HackHub.repository.HackathonRepository;
import unicam.ids.HackHub.repository.SubmissionRepository;
import unicam.ids.HackHub.repository.TeamRepository;
import unicam.ids.HackHub.repository.UserRepository;
import unicam.ids.HackHub.services.SubmissionService;
import unicam.ids.HackHub.services.UserService;

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
    public ResponseEntity<List<Submission>> getSubmissionsListByHackathonId(@RequestBody UserHackathonDTO userHackathonDTO) {
        try {
            Optional<User> user = userRepository.findByEmail(userHackathonDTO.getUserDTO().getEmail());
            if(!userService.isStaff(user.get())) {
                throw new IllegalArgumentException("Accesso negato");
            } else {
                List<Submission> submmissions = submissionService.getSubmissionsByHackathonId(userHackathonDTO.getHackathonDTO().getId());
                return ResponseEntity.ok(submmissions);
            }
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/listByTeam")
    public ResponseEntity<List<Submission>> getSubmissionsListByTeamId(@RequestBody UserDTO userDTO) {
        try {
            Optional<User> user = userRepository.findByEmail(userDTO.getEmail());
            if(user.isEmpty()) {
                throw new IllegalArgumentException("Utente non trovato");
            }
            List<Submission> submissions = submissionService.getSubmissionsByTeamId(user.get().getTeam().getId());
            return ResponseEntity.ok(submissions);
        }
        catch(Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateSubmission(@RequestBody SubmissionDTO submissionDTO) {
        try {
            submissionService.updateSubmission(submissionDTO.getId(), submissionDTO.getTitle(), submissionDTO.getContent(), submissionDTO.getSendingDate(), submissionDTO.getLastEdit());
            return ResponseEntity.ok("Sottomissione modificata");
        }
        catch(Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
