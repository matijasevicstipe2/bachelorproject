package hr.smatijasevic.bachelorproject.gym;

import hr.smatijasevic.bachelorproject.exception.ResourceNotFoundException;
import hr.smatijasevic.bachelorproject.visits.GymVisitRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public List<GymDto> convertToGymDtoList(List<Gym> gyms) {
        return gyms.stream()
                .map(this::convertToGymDto)
                .collect(Collectors.toList());
    }

    @Override
    public GymDto convertToGymDto(Gym gym) {
        return GymDto.builder()
                .id(gym.getId())
                .name(gym.getGymName())
                .address(gym.getAddress())
                .cityState(gym.getCityState())
                .openingHours(gym.getOpeningHours())
                .profilePicture(gym.getProfilePicturePath())
                .build();
    }

}

