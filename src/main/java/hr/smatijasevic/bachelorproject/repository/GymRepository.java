package hr.smatijasevic.bachelorproject.repository;

import hr.smatijasevic.bachelorproject.entity.Gym;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GymRepository extends JpaRepository<Gym, Long> {
}
