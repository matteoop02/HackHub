package unicam.ids.HackHub.state;

import unicam.ids.HackHub.dto.requests.HackathonSubmissionEvaluationRequest;
import unicam.ids.HackHub.enums.HackathonStatus;
import unicam.ids.HackHub.enums.SubmissionStatus;
import unicam.ids.HackHub.model.Hackathon;
import unicam.ids.HackHub.model.Report;
import unicam.ids.HackHub.model.Submission;
import unicam.ids.HackHub.model.Team;
import unicam.ids.HackHub.strategy.WinnerStrategy;

import java.util.Comparator;
import java.util.List;

public class EvaluationState implements HackathonState {
    @Override
    public void signTeam(Hackathon hackathon, Team team) {
        throw new IllegalStateException("hackathon in valutazione");
    }

    @Override
    public void unsubscribeTeamToHackathon(Hackathon hackathon, Team team) {
        throw new IllegalStateException("hackathon in valutazione");
    }

    @Override
    public void evaluateHackathonSubmission(HackathonSubmissionEvaluationRequest request, Submission submission) {
        submission.setScore(request.score());
        submission.setComment(request.comment());
        submission.setState(SubmissionStatus.VALUTATA);
    }

    @Override
    public Submission createSubmission(String title, String content, Team team) {
        throw new IllegalStateException("hackathon non in iscrizione");
    }

    @Override
    public void declareWinner(Hackathon hackathon, List<Submission> submissions, WinnerStrategy strategy) {
        Team winnerTeam = strategy.calculateWinner(submissions);
        hackathon.setTeamWinner(winnerTeam);
        hackathon.setState(HackathonStatus.CONCLUSO);
        hackathon.getObservable().notifyObservers(hackathon, "CAMBIO STATO HACKATHON", "SI è CONCLUSO L'HACKATHON " + hackathon.getName() + ".\nIl vincitore è il team: " + winnerTeam.getName());
    }
}
