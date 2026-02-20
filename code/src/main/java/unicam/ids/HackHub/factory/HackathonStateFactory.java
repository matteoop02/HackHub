package unicam.ids.HackHub.factory;

import unicam.ids.HackHub.enums.HackathonState;
import unicam.ids.HackHub.model.state.*;

public class HackathonStateFactory {

    public static unicam.ids.HackHub.model.state.HackathonState from(HackathonState status) {
        return switch(status) {
            case IN_ISCRIZIONE -> new RegistrationState();
            case IN_CORSO -> new RunningState();
            case IN_VALUTAZIONE -> new EvaluationState();
            case CONCLUSO -> new CompletedState();
        };
    }
}
