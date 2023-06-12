package hr.smatijasevic.bachelorproject.membership;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipOptionRepository extends JpaRepository<MembershipOption, Long> {
}

