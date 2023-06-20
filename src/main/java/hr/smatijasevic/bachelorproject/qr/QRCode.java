package hr.smatijasevic.bachelorproject.qr;


import hr.smatijasevic.bachelorproject.security.user.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "qr_codes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QRCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "qr_pass")
    private String qrPass;

    @Lob
    private byte[] image;


}

