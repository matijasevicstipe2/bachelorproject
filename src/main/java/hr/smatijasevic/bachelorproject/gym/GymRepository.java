package hr.smatijasevic.bachelorproject.gym;

import hr.smatijasevic.bachelorproject.gym.Gym;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GymRepository extends JpaRepository<Gym, Long> {
}
