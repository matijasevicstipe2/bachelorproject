package hr.smatijasevic.bachelorproject.userdetails;


import hr.smatijasevic.bachelorproject.groupclass.GroupClass;
import hr.smatijasevic.bachelorproject.membership.MembershipOption;
import hr.smatijasevic.bachelorproject.security.user.Account;
import hr.smatijasevic.bachelorproject.visits.GymVisit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user_details")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "membership_option_id")
    private MembershipOption membershipOption;

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @Column(name = "usage", nullable = false)
    private int usage;

    private boolean active;
    @Column(name = "in_gym")
    private boolean inGym;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_group_class",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_class_id")
    )
    private Set<GroupClass> groupClasses = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GymVisit> gymVisits = new ArrayList<>();
}
