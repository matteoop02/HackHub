package unicam.ids.HackHub.observer;

import unicam.ids.HackHub.model.Hackathon;
import unicam.ids.HackHub.model.User;

import java.util.HashSet;
import java.util.Set;

public class HackathonObservable {
    private Hackathon hackathon;
    private Set<HackathonObserver> observers = new HashSet<>();

    public HackathonObservable(Hackathon hackathon) {
        this.hackathon = hackathon;
    }

    public void attach(HackathonObserver observer) {
        observers.add(observer);
    }

    public void detach(HackathonObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Hackathon hackathon, String subject, String message) {
        for (HackathonObserver observer : observers) {
            observer.update(hackathon, subject, message);
        }
    }
}
