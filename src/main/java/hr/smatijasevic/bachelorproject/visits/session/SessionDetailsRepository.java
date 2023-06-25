package hr.smatijasevic.bachelorproject.visits.session;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionDetailsRepository extends JpaRepository<SessionDetails, Long> {
}
