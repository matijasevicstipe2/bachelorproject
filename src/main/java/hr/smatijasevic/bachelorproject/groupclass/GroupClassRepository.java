package hr.smatijasevic.bachelorproject.groupclass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupClassRepository extends JpaRepository<GroupClass, Long> {

    List<GroupClass> findGroupClassByGym_Id(Long gymId);
}
