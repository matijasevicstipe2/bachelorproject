package hr.smatijasevic.bachelorproject.gym;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GymDto {
    private Long id;
    private String name;
    private String address;
    private String cityState;
    private String openingHours;
    private Integer peopleInGym;
    private String profilePicture;
}
