package hr.smatijasevic.bachelorproject.visits;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "session_details")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "gym_visit_id")
    private GymVisit gymVisit;

    private String title;

    private String notes;

}
