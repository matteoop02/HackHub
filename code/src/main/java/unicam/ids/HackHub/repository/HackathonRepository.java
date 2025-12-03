package unicam.ids.HackHub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unicam.ids.HackHub.enums.HackathonState;
import unicam.ids.HackHub.model.Hackathon;
import java.util.List;

public interface HackathonRepository extends JpaRepository<Hackathon, Long> {

    //Metodo per evitare la creazione di hackathon con lo stesso nome
    public boolean existByName(String name);

    // Se vuoi aggiungere filtri opzionali:
    public List<Hackathon> findByPlace(String place);

    public List<Hackathon> findByState(HackathonState state);
}
