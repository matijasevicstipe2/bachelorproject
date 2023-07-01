package hr.smatijasevic.bachelorproject.personaltrainer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainers")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class PersonalTrainerController {

    private final PersonalTrainerService personalTrainerService;

    @GetMapping
    public ResponseEntity<List<TrainerDto>> getAllPersonalTrainers() {
        return new ResponseEntity<>(personalTrainerService
                .convertToTrainerDtoList(personalTrainerService.getAllPersonalTrainers()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainerDto> getPersonalTrainerById(@PathVariable Integer id) {
        PersonalTrainer trainer = personalTrainerService.getPersonalTrainerById(id);
        if (trainer != null) {
            return new ResponseEntity<>(personalTrainerService.convertToTrainerDto(trainer), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Void> savePersonalTrainer(@RequestBody PersonalTrainer personalTrainer) {
        personalTrainerService.savePersonalTrainer(personalTrainer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonalTrainer(@PathVariable Integer id) {
        personalTrainerService.deletePersonalTrainer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}

