package hr.smatijasevic.bachelorproject.qr;

import hr.smatijasevic.bachelorproject.security.user.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QRCodeRepository extends JpaRepository<QRCode, Long> {
    Optional<QRCode> findByAccountAndQrPass(Account account, String qrPass);
    Optional<QRCode> findByAccount(Account account);

}