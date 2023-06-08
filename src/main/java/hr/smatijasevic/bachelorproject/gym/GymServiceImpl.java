package hr.smatijasevic.bachelorproject.gym;

import hr.smatijasevic.bachelorproject.gym.Gym;
import hr.smatijasevic.bachelorproject.gym.GymRepository;
import hr.smatijasevic.bachelorproject.gym.GymService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GymServiceImpl implements GymService {

    private final GymRepository gymRepository;

    public GymServiceImpl(GymRepository gymRepository) {
        this.gymRepository = gymRepository;
    }

    @Override
    public List<Gym> getAllGyms() {
        return gymRepository.findAll();
    }
}

