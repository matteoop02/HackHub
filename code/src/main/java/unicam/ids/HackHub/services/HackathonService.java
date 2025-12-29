package unicam.ids.HackHub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unicam.ids.HackHub.dto.HackathonDTO;
import unicam.ids.HackHub.enums.HackathonState;
import unicam.ids.HackHub.model.Hackathon;
import unicam.ids.HackHub.model.Submission;
import unicam.ids.HackHub.model.Team;
import unicam.ids.HackHub.model.User;
import unicam.ids.HackHub.repository.HackathonRepository;
import unicam.ids.HackHub.repository.TeamRepository;
import unicam.ids.HackHub.repository.UserRepository;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class HackathonService {
    @Autowired
    private HackathonRepository hackathonRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TeamRepository teamRepository;

    @Transactional(readOnly = true)
    public List<Hackathon> getAllHackathons() {
        return hackathonRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<HackathonDTO> getAllHackathonsAsDTO() {
        return hackathonRepository.findAll().stream()
                .map(h -> new HackathonDTO(
                        h.getId(),
                        h.getName(),
                        h.getPlace(),
                        h.getRegulation(),
                        h.getSubscriptionDeadline(),
                        h.getStartDate(),
                        h.getEndDate(),
                        h.getReward(),
                        h.getTeams(),
                        h.getMaxTeamSize(),
                        h.getWinner() != null ? h.getWinner().getId() : null,
                        h.getState()
                ))
                .toList();
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

    @Transactional(readOnly = true)
    public List<HackathonDTO> getPublicHackathons() {
        List<Hackathon> allHackathons = hackathonRepository.findAll();
        return allHackathons.stream()
                .map(h -> new HackathonDTO(
                        h.getId(),
                        h.getName(),
                        h.getPlace(),
                        null,
                        h.getSubscriptionDeadline(),
                        h.getStartDate(),
                        h.getEndDate(),
                        h.getReward(),
                        null,
                        h.getMaxTeamSize(),
                        null,
                        h.getState()
                ))
                .toList();
    }

    @Transactional
    public void signTeamToHackathon(Long userId, Long hackathonId, Long teamId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) {
            throw new IllegalArgumentException("Utente non trovato");
        }
        Optional<Hackathon> hackathon = hackathonRepository.findById(hackathonId);
        if(hackathon.isEmpty()) {
            throw new IllegalArgumentException("Hackathon non trovato");
        }
        Optional<Team> team = teamRepository.findById(teamId);
        if(team.isEmpty()) {
            throw new IllegalArgumentException("Team non trovato");
        }
        if(!team.get().getTeamLeader().getId().equals(userId)) {
            throw new IllegalArgumentException("Accesso negato");
        }
        if (hackathon.get().getState() != HackathonState.IN_ISCRIZIONE) {
            throw new IllegalArgumentException("Hackathon non aperto alle iscrizioni");
        }

        if (new Date().after(hackathon.get().getSubscriptionDeadline())) {
            throw new IllegalArgumentException("Scadenza iscrizioni superata");
        }

        if (team.get().getHackathon() != null) {
            throw new IllegalArgumentException("Il team è già iscritto a un hackathon");
        }

        if (team.get().getMembers().size() > hackathon.get().getMaxTeamSize()) {
            throw new IllegalArgumentException("Numero membri superiore al massimo consentito");
        }
        team.get().setHackathon(hackathon.get());
        hackathon.get().getTeams().add(team.get());
    }
}


