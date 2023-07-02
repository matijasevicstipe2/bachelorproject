package hr.smatijasevic.bachelorproject.personaltrainer;


import hr.smatijasevic.bachelorproject.groupclass.GroupClass;
import hr.smatijasevic.bachelorproject.gym.Gym;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "personal_trainers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalTrainer {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "firstname", nullable = false)
    private String firstname;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "availability", nullable = false)
    private boolean availability;

    @ManyToOne
    @JoinColumn(name = "gym_id")
    private Gym gym;

    @Column(name = "speciality")
    private String speciality;

    @Lob
    @Column(name = "profilepicture")
    private byte[] profilePicture;

    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupClass> groupClasses = new ArrayList<>();

}

