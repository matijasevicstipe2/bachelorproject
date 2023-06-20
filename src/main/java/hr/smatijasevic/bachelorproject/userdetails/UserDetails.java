package hr.smatijasevic.bachelorproject.userdetails;


import hr.smatijasevic.bachelorproject.membership.MembershipOption;
import hr.smatijasevic.bachelorproject.security.user.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

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

    @OneToOne
    @JoinColumn(name = "membership_option_id")
    private MembershipOption membershipOption;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "usage", nullable = false)
    private int usage;

    private boolean active;
    @Column(name = "in_gym")
    private boolean inGym;
}
