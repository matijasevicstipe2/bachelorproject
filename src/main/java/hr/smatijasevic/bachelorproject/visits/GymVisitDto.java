package hr.smatijasevic.bachelorproject.visits;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class GymVisitDto {
    private LocalDateTime enterTime;
    private LocalDateTime exitTime;
    private String title;
    private String notes;

}
