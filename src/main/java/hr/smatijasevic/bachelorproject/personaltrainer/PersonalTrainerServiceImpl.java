package hr.smatijasevic.bachelorproject.personaltrainer;

import hr.smatijasevic.bachelorproject.gym.GymService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonalTrainerServiceImpl implements PersonalTrainerService {

    private final PersonalTrainerRepository personalTrainerRepository;
    private final GymService gymService;

    public PersonalTrainerServiceImpl(PersonalTrainerRepository personalTrainerRepository, GymService gymService) {
        this.personalTrainerRepository = personalTrainerRepository;
        this.gymService = gymService;
    }

    @Override
    public List<PersonalTrainer> getAllPersonalTrainers() {
        return personalTrainerRepository.findAll();
    }

    @Override
    public PersonalTrainer getPersonalTrainerById(Integer id) {
        return personalTrainerRepository.findById(id).orElse(null);
    }

    @Override
    public void savePersonalTrainer(PersonalTrainer personalTrainer) {
        personalTrainerRepository.save(personalTrainer);
    }

    @Override
    public void deletePersonalTrainer(Integer id) {
        personalTrainerRepository.deleteById(id);
    }

    @Override
    public List<TrainerDto> convertToTrainerDtoList(List<PersonalTrainer> personalTrainers) {
        return personalTrainers.stream()
                .map(this::convertToTrainerDto)
                .collect(Collectors.toList());
    }

    @Override
    public TrainerDto convertToTrainerDto(PersonalTrainer personalTrainer) {
        return TrainerDto.builder()
                .id(personalTrainer.getId())
                .firstName(personalTrainer.getFirstname())
                .lastName(personalTrainer.getLastname())
                .age(personalTrainer.getAge())
                .email(personalTrainer.getEmail())
                .availability(personalTrainer.isAvailability())
                .gym(gymService.convertToGymDto(personalTrainer.getGym()))
                .specialty(personalTrainer.getSpeciality())
                .profilePicture(personalTrainer.getProfilePicture())
                .build();
    }
}

