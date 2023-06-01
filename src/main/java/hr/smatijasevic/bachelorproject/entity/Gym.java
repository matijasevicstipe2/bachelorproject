package hr.smatijasevic.bachelorproject.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "gyms")
public class Gym {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gymname")
    private String gymName;

    private String address;

    @Column(name = "citystate")
    private String cityState;

    // Constructors, getters, and setters

    public Gym() {
    }

    public Gym(String gymName, String address, String cityState) {
        this.gymName = gymName;
        this.address = address;
        this.cityState = cityState;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGymName() {
        return gymName;
    }

    public void setGymName(String gymName) {
        this.gymName = gymName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCityState() {
        return cityState;
    }

    public void setCityState(String cityState) {
        this.cityState = cityState;
    }
}
