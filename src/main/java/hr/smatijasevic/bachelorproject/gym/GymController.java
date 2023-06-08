package hr.smatijasevic.bachelorproject.gym;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class GymController {

    private final GymService gymService;

    @GetMapping("/gyms")
    public List<Gym> getAllGyms() {
        return gymService.getAllGyms();
    }
}
