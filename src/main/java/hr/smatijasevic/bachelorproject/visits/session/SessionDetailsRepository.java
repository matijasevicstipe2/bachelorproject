package hr.smatijasevic.bachelorproject.visits.session;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionDetailsRepository extends JpaRepository<SessionDetails, Long> {

    Optional<SessionDetails> findByGymVisit_Id(Long id);
}
