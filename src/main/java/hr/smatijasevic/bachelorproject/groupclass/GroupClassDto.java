package hr.smatijasevic.bachelorproject.groupclass;

import hr.smatijasevic.bachelorproject.gym.GymDto;
import hr.smatijasevic.bachelorproject.personaltrainer.TrainerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupClassDto {
    private Long id;
    private String name;
    private TrainerDto trainer;
    private Long maxPeople;
    private GymDto gym;
    private LocalDateTime schedule;
    private Long duration;
}

