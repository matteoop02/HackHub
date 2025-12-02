package unicam.ids.HackHub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unicam.ids.HackHub.model.Hackathon;
import unicam.ids.HackHub.model.User;
import unicam.ids.HackHub.repository.HackathonRepository;

import java.util.Date;

@Service
public class HackathonService {

    @Autowired
    private HackathonRepository hackathonRepository;

    @Transactional
    public Hackathon CreateHackathon(User creator, String name, String place, String regulation, Date subscriptionDeadline, Date startDate, Date endDate, double prize, int maxTeamSize) {
        if(creator.getRole().getName() == null || creator.getRole().getName() != "ORGANIZZATORE") {
            throw new IllegalArgumentException("Solo l'organizzatore può creare un hackathon");
        }
        if(!startDate.before(endDate)) {
            throw new IllegalArgumentException("Le data di inizio non è antecedente alla data di fine hackathon");
        }
        if(!subscriptionDeadline.before(startDate)) {
            throw new IllegalArgumentException("La data di scadenza iscrizione non è antecedente alla data inizio");
        }
        if(hackathonRepository.existByName(name)) {
            throw new IllegalArgumentException("Esiste già un hackathon con lo stesso nome");
        }
        Hackathon hackathon = new Hackathon(name, place, regulation, subscriptionDeadline, startDate, endDate, prize, maxTeamSize);
        return hackathonRepository.save(hackathon);
    }

}
