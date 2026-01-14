package unicam.ids.HackHub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unicam.ids.HackHub.model.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
}
