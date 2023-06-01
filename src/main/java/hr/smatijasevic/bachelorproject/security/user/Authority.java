package hr.smatijasevic.bachelorproject.security.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;



@Entity
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "authority_name", length = 50, nullable = false)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
