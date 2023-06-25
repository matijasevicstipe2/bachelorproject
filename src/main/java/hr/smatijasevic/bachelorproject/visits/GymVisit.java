package hr.smatijasevic.bachelorproject.visits;


import hr.smatijasevic.bachelorproject.gym.Gym;
import hr.smatijasevic.bachelorproject.userdetails.UserDetails;
import hr.smatijasevic.bachelorproject.visits.session.SessionDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "gym_visits")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GymVisit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDetails user;
    @ManyToOne
    @JoinColumn(name = "gym_id")
    private Gym gym;

    @Column(name = "enter_time")
    private LocalDateTime enterTime;

    @Column(name = "exit_time")
    private LocalDateTime exitTime;
    @OneToOne(mappedBy = "gymVisit")
    private SessionDetails sessionDetails;
}
