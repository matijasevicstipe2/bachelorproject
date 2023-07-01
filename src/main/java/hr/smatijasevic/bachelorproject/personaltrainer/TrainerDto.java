package hr.smatijasevic.bachelorproject.personaltrainer;

import hr.smatijasevic.bachelorproject.gym.GymDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private boolean availability;
    private GymDto gym;
    private String specialty;
    private String profilePicture;
}