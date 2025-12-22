package unicam.ids.HackHub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unicam.ids.HackHub.model.Hackathon;
import unicam.ids.HackHub.model.Submission;
import unicam.ids.HackHub.model.User;
import unicam.ids.HackHub.repository.HackathonRepository;
import java.util.Date;
import java.util.List;

@Service
public class HackathonService {

    @Autowired
    private HackathonRepository hackathonRepository;

    @Transactional(readOnly = true)
    public List<Hackathon> getAllHackathons() {
        return hackathonRepository.findAll();
    }

    @Transactional
    public Hackathon createHackathon(
            User creator,
            String name,
            String place,
            String regulation,
            Date subscriptionDeadline,
            Date startDate,
            Date endDate,
            double reward,
            int maxTeamSize) {

        validateHackathonCreation(creator, name, subscriptionDeadline, startDate, endDate);

        Hackathon hackathon = new Hackathon();
        hackathon.setName(name);
        hackathon.setPlace(place);
        hackathon.setRegulation(regulation);
        hackathon.setSubscriptionDeadline(subscriptionDeadline);
        hackathon.setStartDate(startDate);
        hackathon.setEndDate(endDate);
        hackathon.setReward(reward);
        hackathon.setMaxTeamSize(maxTeamSize);

        return hackathonRepository.save(hackathon);
    }

    private void validateHackathonCreation(User creator, String name, Date subscriptionDeadline, Date startDate, Date endDate) {

        if (!creator.getRole().getName().equals("ORGANIZZATORE"))
            throw new IllegalArgumentException("Solo l'organizzatore può creare un hackathon");

        if (!startDate.before(endDate))
            throw new IllegalArgumentException("La data di inizio non è antecedente alla data di fine hackathon");

        if (!subscriptionDeadline.before(startDate))
            throw new IllegalArgumentException("La data di scadenza iscrizione non è antecedente alla data inizio");

        if (hackathonRepository.existsByName(name))
            throw new IllegalArgumentException("Esiste già un hackathon con lo stesso nome");
    }
}


