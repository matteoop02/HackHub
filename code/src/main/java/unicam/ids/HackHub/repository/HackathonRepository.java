package unicam.ids.HackHub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unicam.ids.HackHub.model.Hackathon;

public interface HackathonRepository extends JpaRepository<Hackathon, Long> {

    //Metodo per evitare la creazione di hackathon con lo stesso nome
    public boolean existByName(String name);
}
