package hr.smatijasevic.bachelorproject.gym;

import hr.smatijasevic.bachelorproject.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Gym getGymById(Long id) {
        return gymRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gym not found with id: " + id));
    }
}

