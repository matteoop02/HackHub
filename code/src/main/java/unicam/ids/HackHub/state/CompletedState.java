package unicam.ids.HackHub.state;

import unicam.ids.HackHub.dto.requests.HackathonSubmissionEvaluationRequest;
import unicam.ids.HackHub.enums.SubmissionStatus;
import unicam.ids.HackHub.model.Hackathon;
import unicam.ids.HackHub.model.Report;
import unicam.ids.HackHub.model.Submission;
import unicam.ids.HackHub.model.Team;
import unicam.ids.HackHub.strategy.WinnerStrategy;

import java.util.List;

public class CompletedState implements HackathonState {
    @Override
    public void signTeam(Hackathon hackathon, Team team) {
        throw new IllegalStateException("Hackathon concluso");
    }

    @Override
    public void unsubscribeTeamToHackathon(Hackathon hackathon, Team team) {
        throw new IllegalStateException("hackathon concluso");
    }

    @Override
    public void evaluateHackathonSubmission(HackathonSubmissionEvaluationRequest request, Submission submission) {
        throw new IllegalStateException("hackathon non in valutazione");
    }

    @Override
    public Submission createSubmission(String title, String content, Team team) {
        throw new IllegalStateException("hackathon non in iscrizione");
    }

    @Override
    public void declareWinner(Hackathon hackathon, List<Submission> submissions, WinnerStrategy strategy) {
        throw new IllegalStateException("Vincitore già dichiarato");
    }
}
