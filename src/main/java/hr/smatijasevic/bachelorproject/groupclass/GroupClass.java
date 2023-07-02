package hr.smatijasevic.bachelorproject.groupclass;


import hr.smatijasevic.bachelorproject.gym.Gym;
import hr.smatijasevic.bachelorproject.personaltrainer.PersonalTrainer;
import hr.smatijasevic.bachelorproject.userdetails.UserDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "group_classes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "trainer_id", nullable = false)
    private PersonalTrainer trainer;

    @Column(name = "max_people")
    private Long maxPeople;

    @ManyToOne
    @JoinColumn(name = "gym_id", nullable = false)
    private Gym gym;

    @Column(name = "schedule", nullable = false)
    private LocalDateTime schedule;

    @Column(name = "duration", nullable = false)
    private Long duration;

    @ManyToMany(mappedBy = "groupClasses")
    private Set<UserDetails> users = new HashSet<>();
}
