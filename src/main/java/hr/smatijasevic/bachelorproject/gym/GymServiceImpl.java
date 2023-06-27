package hr.smatijasevic.bachelorproject.gym;

import hr.smatijasevic.bachelorproject.exception.ResourceNotFoundException;
import hr.smatijasevic.bachelorproject.visits.GymVisitRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GymServiceImpl implements GymService {

    private final GymRepository gymRepository;
    private final GymVisitRepository gymVisitRepository;

    public GymServiceImpl(GymRepository gymRepository, GymVisitRepository gymVisitRepository) {
        this.gymRepository = gymRepository;
        this.gymVisitRepository = gymVisitRepository;
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

    @Override
    public Map<Long, Integer> getPeopleInGym() {
        List<Object[]> results = gymVisitRepository.calculatePeopleInGym();
        Map<Long, Integer> peopleInGymMap = new HashMap<>();

        for (Object[] result : results) {
            Long gymId = (Long) result[0];
            Integer peopleCount = ((Number) result[1]).intValue();
            peopleInGymMap.put(gymId, peopleCount);
        }

        return peopleInGymMap;
    }
}

