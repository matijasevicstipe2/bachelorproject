package hr.smatijasevic.bachelorproject.personaltrainer;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonalTrainerServiceImpl implements PersonalTrainerService {

    private final PersonalTrainerRepository personalTrainerRepository;

    public PersonalTrainerServiceImpl(PersonalTrainerRepository personalTrainerRepository) {
        this.personalTrainerRepository = personalTrainerRepository;
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
}

