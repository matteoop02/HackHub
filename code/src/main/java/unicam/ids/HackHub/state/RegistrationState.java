package unicam.ids.HackHub.state;

import unicam.ids.HackHub.dto.requests.CreateTeamSubmissionRequest;
import unicam.ids.HackHub.dto.requests.HackathonSubmissionEvaluationRequest;
import unicam.ids.HackHub.enums.SubmissionStatus;
import unicam.ids.HackHub.exceptions.HackathonClosedException;
import unicam.ids.HackHub.exceptions.TeamAlreadyRegisteredException;
import unicam.ids.HackHub.exceptions.TeamTooLargeException;
import unicam.ids.HackHub.model.Hackathon;
import unicam.ids.HackHub.model.Report;
import unicam.ids.HackHub.model.Submission;
import unicam.ids.HackHub.model.Team;
import unicam.ids.HackHub.strategy.WinnerStrategy;

import java.time.LocalDateTime;
import java.util.List;

public class RegistrationState implements HackathonState {

    @Override
    public void signTeam(Hackathon hackathon, Team team) {
        if (team.getHackathon() != null)
            throw new TeamAlreadyRegisteredException("Il team è già iscritto a un hackathon");
        if (team.getMembers().size() > hackathon.getMaxTeamSize())
            throw new TeamTooLargeException("Numero membri superiore al massimo consentito");
        team.setHackathon(hackathon);
        hackathon.getTeams().add(team);
    }

    @Override
    public void unsubscribeTeamToHackathon(Hackathon hackathon, Team team) {
        hackathon.getTeams().remove(team);
    }

    @Override
    public void evaluateHackathonSubmission(HackathonSubmissionEvaluationRequest request, Submission submission) {
        throw new IllegalStateException("hackathon non in valutazione");
    }

    @Override
    public Submission createSubmission(String title, String content, Team team) {
        return Submission.builder()
                .title(title)
                .content(content)
                .sendingDate(LocalDateTime.now())
                .lastEdit(LocalDateTime.now())
                .state(SubmissionStatus.INVIATA)
                .team(team)
                .hackathon(team.getHackathon())
                .build();
    }

    @Override
    public void declareWinner(Hackathon hackathon, List<Submission> submissions, WinnerStrategy strategy) {
        throw new IllegalStateException("valutezioni non concluse");
    }
}
