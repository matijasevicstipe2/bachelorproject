package hr.smatijasevic.bachelorproject.groupclass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupClassRepository extends JpaRepository<GroupClass, Long> {
}
