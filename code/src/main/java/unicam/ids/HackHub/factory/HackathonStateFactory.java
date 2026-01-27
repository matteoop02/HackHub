package unicam.ids.HackHub.factory;

import unicam.ids.HackHub.enums.HackathonStatus;
import unicam.ids.HackHub.state.*;

public class HackathonStateFactory {

    public static HackathonState from(HackathonStatus status) {
        return switch(status) {
            case IN_ISCRIZIONE -> new RegistrationState();
            case IN_CORSO -> new RunningState();
            case IN_VALUTAZIONE -> new EvaluationState();
            case CONCLUSO -> new CompletedState();
        };
    }
}
