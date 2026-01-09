package unicam.ids.HackHub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unicam.ids.HackHub.dto.requests.CreateHackathonRequest;
import unicam.ids.HackHub.enums.HackathonState;
import unicam.ids.HackHub.exceptions.ResourceNotFoundException;
import unicam.ids.HackHub.model.Hackathon;
import unicam.ids.HackHub.model.Team;
import unicam.ids.HackHub.model.User;
import unicam.ids.HackHub.repository.HackathonRepository;
import unicam.ids.HackHub.repository.SubmissionRepository;
import unicam.ids.HackHub.repository.TeamRepository;
import unicam.ids.HackHub.repository.UserRepository;

import java.util.*;

@Service
public class HackathonService {
    @Autowired
    private HackathonRepository hackathonRepository;
    @Autowired
    private SubmissionRepository submissionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private TeamService teamService;

    @Transactional(readOnly = true)
    public List<Hackathon> getHackathons() {
        return hackathonRepository.findAll();
    }

    public List<Hackathon> getHackathonsPublic() { return hackathonRepository.findAllByIsPublic(true); }

    @Transactional
    public Hackathon createHackathon(CreateHackathonRequest createHackathonRequest) {

        validateHackathonCreation(createHackathonRequest.getName(), createHackathonRequest.getSubscriptionDeadline(),
                createHackathonRequest.getStartDate(), createHackathonRequest.getEndDate());

        Hackathon hackathon = new Hackathon();
        hackathon.setName(createHackathonRequest.getName());
        hackathon.setPlace(createHackathonRequest.getPlace());
        hackathon.setRegulation(createHackathonRequest.getRegulation());
        hackathon.setSubscriptionDeadline(createHackathonRequest.getSubscriptionDeadline());
        hackathon.setStartDate(createHackathonRequest.getStartDate());
        hackathon.setEndDate(createHackathonRequest.getEndDate());
        hackathon.setReward(createHackathonRequest.getReward());
        hackathon.setMaxTeamSize(createHackathonRequest.getMaxTeamSize());

        hackathonRepository.save(hackathon);
        return hackathon;
    }

    private void validateHackathonCreation(String name, Date subscriptionDeadline, Date startDate, Date endDate) {
        if (!startDate.before(endDate))
            throw new IllegalArgumentException("La data di inizio non è antecedente alla data di fine hackathon");

        if (!subscriptionDeadline.before(startDate))
            throw new IllegalArgumentException("La data di scadenza iscrizione non è antecedente alla data inizio");

        if (hackathonRepository.existsByName(name))
            throw new IllegalArgumentException("Esiste già un hackathon con lo stesso nome");
    }

    @Transactional
    public void signTeamToHackathon(String username, String hackathonName) {
        //Cerco se l'utente esiste
        User user = userService.findUserByUsername(username);

        //Cerco se l'hackathon esiste
        Hackathon hackathon = hackathonRepository.findHackathonByName(hackathonName)
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon inesistente"));

        Team team = teamService.findByName(user.getTeam().getName());

        if (hackathon.getState() != HackathonState.IN_ISCRIZIONE) {
            throw new IllegalArgumentException("Hackathon non aperto alle iscrizioni");
        }
        //Da capire forse non serve perchè cambia stato al passare della data
        else if (new Date().after(hackathon.getSubscriptionDeadline())) {
            throw new IllegalArgumentException("Scadenza iscrizioni superata");
        }
        else if (team.getHackathon() != null) {
            throw new IllegalArgumentException("Il team è già iscritto a un hackathon");
        }
        else if (team.getMembers().size() > hackathon.getMaxTeamSize()) {
            throw new IllegalArgumentException("Numero membri superiore al massimo consentito");
        }

        team.setHackathon(hackathon);
        hackathon.getTeams().add(team);
    }

    public Hackathon getHackathonByName(String name) {
        return hackathonRepository.findHackathonByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon non trovato"));
    }

    public Hackathon findHackathonByName(String hackathonName) {
        return hackathonRepository.findHackathonByName(hackathonName)
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon non trovato"));
    }
}


