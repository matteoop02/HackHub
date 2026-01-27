package unicam.ids.HackHub.strategy;

import unicam.ids.HackHub.enums.SubmissionStatus;
import unicam.ids.HackHub.model.Submission;
import unicam.ids.HackHub.model.Team;

import java.util.Comparator;
import java.util.List;

public class HighestScoreStrategy implements WinnerStrategy {

    @Override
    public Team calculateWinner(List<Submission> submissions) {
        Submission bestSubmission = submissions.stream()
                .filter(s -> s.getState() == SubmissionStatus.VALUTATA)
                .filter(s -> s.getScore() != null)
                .max(Comparator.comparing(Submission::getScore))
                .orElseThrow(() -> new IllegalStateException("Nessuna submission valutata disponibile"));
        return bestSubmission.getTeam();
    }
}
