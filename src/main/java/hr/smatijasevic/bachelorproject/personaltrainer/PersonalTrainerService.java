package hr.smatijasevic.bachelorproject.personaltrainer;

import java.util.List;

public interface PersonalTrainerService {

    List<PersonalTrainer> getAllPersonalTrainers();

    PersonalTrainer getPersonalTrainerById(Integer id);

    void savePersonalTrainer(PersonalTrainer personalTrainer);

    void deletePersonalTrainer(Integer id);
    List<TrainerDto> convertToTrainerDtoList(List<PersonalTrainer> personalTrainers);
    TrainerDto convertToTrainerDto(PersonalTrainer personalTrainer);

}

