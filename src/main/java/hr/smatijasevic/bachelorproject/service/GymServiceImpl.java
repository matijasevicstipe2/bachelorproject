package hr.smatijasevic.bachelorproject.service;

import hr.smatijasevic.bachelorproject.entity.Gym;
import hr.smatijasevic.bachelorproject.repository.GymRepository;
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

