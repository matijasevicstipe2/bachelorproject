package hr.smatijasevic.bachelorproject.groupclass;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_group_class")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGroupClass {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "group_class_id")
    private Long groupClassId;
}