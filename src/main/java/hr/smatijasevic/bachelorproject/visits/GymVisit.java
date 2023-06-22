package hr.smatijasevic.bachelorproject.visits;


import hr.smatijasevic.bachelorproject.security.user.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "enter_time")
    private LocalDateTime enterTime;

    @Column(name = "exit_time")
    private LocalDateTime exitTime;
}
