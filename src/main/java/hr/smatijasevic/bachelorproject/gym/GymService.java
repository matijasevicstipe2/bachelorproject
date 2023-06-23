package hr.smatijasevic.bachelorproject.gym;

import java.util.List;
import java.util.Optional;

public interface GymService {
    List<Gym> getAllGyms();
    Gym getGymById(Long id);
}
