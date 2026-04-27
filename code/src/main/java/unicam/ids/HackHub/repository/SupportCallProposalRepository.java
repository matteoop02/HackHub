package unicam.ids.HackHub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unicam.ids.HackHub.model.SupportCallProposal;

import java.util.List;

public interface SupportCallProposalRepository extends JpaRepository<SupportCallProposal, Long> {
    List<SupportCallProposal> findByMentorIdOrderByProposedStartTimeAsc(Long mentorId);
}
