package unicam.ids.HackHub.observer;

import unicam.ids.HackHub.model.Hackathon;

public interface HackathonObserver {
    void update(Hackathon hackathon, String subject, String message);
}
