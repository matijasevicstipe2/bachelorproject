package hr.smatijasevic.bachelorproject.qr;

import hr.smatijasevic.bachelorproject.membership.MembershipOption;
import hr.smatijasevic.bachelorproject.membership.MembershipOptionService;
import hr.smatijasevic.bachelorproject.security.user.Account;
import hr.smatijasevic.bachelorproject.security.user.AccountRepository;
import hr.smatijasevic.bachelorproject.userdetails.UserDetails;
import hr.smatijasevic.bachelorproject.userdetails.UserDetailsService;
import hr.smatijasevic.bachelorproject.visits.GymVisit;
import hr.smatijasevic.bachelorproject.visits.GymVisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class QRController {

    // Encryption parameters
    private static final String key = "91eef5e40b3d00128bc4a685b03be158";
    private static final String iv = "091354769e141411d9d2784ebe73231a";
    private final QRCodeService qrCodeService;
    private final AccountRepository accountRepository;
    private final UserDetailsService userDetailsService;
    private final MembershipOptionService membershipOptionService;
    private final GymVisitService gymVisitService;

    @PostMapping("/api/qrdata")
    public String processScannedData(@RequestBody QRDataDto qrDataDto) {
        String decryptedQR = "";
        String data = qrDataDto.getData();
        LocalDateTime dateTime = qrDataDto.getDatetime();
        System.out.println("Encrypted data: " + data);
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] decodedData = Base64.getDecoder().decode(data);
            byte[] decryptedData = cipher.doFinal(decodedData);
            decryptedQR = new String(decryptedData);

            System.out.println("Decrypted data: " + decryptedQR);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int lastIndex = decryptedQR.length() - 8;
        String username = decryptedQR.substring(0, lastIndex);
        String qrPass = decryptedQR.substring(lastIndex);
        Optional<Account> accOptional = accountRepository.findByUsername(username);
        if (accOptional.isPresent()) {
            Account acc = accOptional.get();
            Optional<QRCode> qrCode = qrCodeService.getQRCodeByAccountAndQrPass(acc, qrPass);
            if (qrCode.isPresent()) {
                UserDetails details = userDetailsService.getUserDetailsByAccount(acc.getId());
                if (details.isActive()) {
                    if (checkMembership(details, dateTime)) {
                        if (details.isInGym()) {
                            GymVisit currentVisit = gymVisitService
                                    .getByAccountAndDate(acc.getId(), LocalDate.now()).get(0);
                            currentVisit.setExitTime(dateTime);
                            gymVisitService.saveGymVisit(currentVisit);
                            details.setInGym(false);
                            return "GYM EXIT";
                        } else {
                            GymVisit currentVisit = GymVisit.builder()
                                    .account(acc)
                                    .enterTime(dateTime)
                                    .build();
                            gymVisitService.saveGymVisit(currentVisit);
                            details.setInGym(true);
                            return "GYM ENTER";
                        }
                    } else {
                        return "You need to renew your membership!";
                    }
                } else {
                    return "QR Code is not active!";
                }
            } else {
                return "QR Code is invalid!";
            }
        } else {
            return "Username does not exists!";
        }
    }

    private boolean checkMembership(UserDetails details, LocalDateTime dateTime) {
        MembershipOption membership = details.getMembershipOption();
        if (membership.getType().equals("T")) {
            if (membership.getDuration() >= gymVisitService
                    .getCountByAccountAndEnterTimeAfter(details.getAccount(), details.getPaymentDate())) {
                return true;
            } else {
                details.setActive(false);
                return false;
            }
        } else {
            if (membership.getDuration() == 365) {
                if (dateTime.isBefore(details.getPaymentDate().plusYears(1))) {
                    return true;
                } else {
                    details.setActive(false);
                    return false;
                }
            } else {
                if (dateTime.isBefore(details.getPaymentDate().plusMonths(1))) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }
}

