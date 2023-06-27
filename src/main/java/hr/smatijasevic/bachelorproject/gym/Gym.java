package hr.smatijasevic.bachelorproject.gym;

import hr.smatijasevic.bachelorproject.groupclass.GroupClass;
import hr.smatijasevic.bachelorproject.personaltrainer.PersonalTrainer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "gyms")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Gym {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gymname")
    private String gymName;

    private String address;

    @Column(name = "citystate")
    private String cityState;

    @Column(name = "opening_hours")
    private String openingHours;

    @Column(name = "profile_picture_path")
    private String profilePicturePath;

    @OneToMany(mappedBy = "gym", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PersonalTrainer> personalTrainers = new ArrayList<>();

    @OneToMany(mappedBy = "gym", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupClass> groupClasses = new ArrayList<>();
}
