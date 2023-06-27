package hr.smatijasevic.bachelorproject.gym;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class GymController {

    private final GymService gymService;

    @GetMapping("/gyms")
    public List<Gym> getAllGyms() {
        return gymService.getAllGyms();
    }

    @GetMapping("/people-in-gym")
    public ResponseEntity<Map<Long, Integer>> getPeopleInGym() {
        return ResponseEntity.ok(gymService.getPeopleInGym());
    }

}
