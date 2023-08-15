package hr.smatijasevic.bachelorproject.visits;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GymVisitDto {
    private Long id;
    private LocalDateTime enterTime;
    private LocalDateTime exitTime;
    private String title;
    private String notes;

}
