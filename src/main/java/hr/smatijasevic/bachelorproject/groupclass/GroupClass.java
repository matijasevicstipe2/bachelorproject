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
    @JoinColumn(name = "trainer_id")
    private PersonalTrainer trainer;

    @ManyToOne
    @JoinColumn(name = "gym_id")
    private Gym gym;

    @Column(name = "schedule")
    private LocalDateTime schedule;

    @ManyToMany(mappedBy = "groupClasses")
    private Set<UserDetails> users = new HashSet<>();
}
