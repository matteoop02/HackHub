package unicam.ids.HackHub.model.declareWinner;

import unicam.ids.HackHub.model.Submission;
import unicam.ids.HackHub.model.Team;

import java.util.List;

public interface WinnerStrategy {
    Team calculateWinner(List<Submission> submissions);
}
