package hr.smatijasevic.bachelorproject.qr;

import hr.smatijasevic.bachelorproject.gym.Gym;
import hr.smatijasevic.bachelorproject.gym.GymService;
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
    byte[] key = {(byte)0x91, (byte)0xee, (byte)0xf5, (byte)0xe4, 0x0b, (byte)0x3d, 0x00, 0x12, (byte)0x8b, (byte)0xc4, (byte)0xa6, (byte)0x85, (byte)0xb0, 0x3b, (byte)0xe1, 0x58};
    byte[] iv = {0x09, 0x13, 0x54, 0x76, (byte)0x9e, 0x14, 0x14, 0x11, (byte)0xd9, (byte)0xd2, 0x78, 0x4e, (byte)0xbe, (byte)0x73, 0x23, 0x1a};


    private final QRCodeService qrCodeService;
    private final AccountRepository accountRepository;
    private final UserDetailsService userDetailsService;
    private final MembershipOptionService membershipOptionService;
    private final GymVisitService gymVisitService;
    private final GymService gymService;

    @PostMapping("/qrdata")
    public String processScannedData(@RequestBody QRDataDto qrDataDto) {
        String decryptedQR = "";
        String data = qrDataDto.getData();
        LocalDateTime dateTime = qrDataDto.getDatetime();
        Gym gym = gymService.getGymById(qrDataDto.getGymId());

        System.out.println("Encrypted data: " + data);
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] decodedData = Base64.getDecoder().decode(data);
            byte[] decryptedData = cipher.doFinal(decodedData);
            decryptedQR = new String(decryptedData, StandardCharsets.UTF_8);

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
                UserDetails details = userDetailsService.getUserDetailsByAccount(acc);
                if (details.isActive()) {
                    if (checkMembershipValid(details, dateTime)) {
                        if (details.isInGym()) {
                            GymVisit currentVisit = gymVisitService
                                    .getAccountVisits(acc.getId(), gym.getId(), LocalDate.now()).get(0);
                            currentVisit.setExitTime(dateTime);
                            gymVisitService.saveGymVisit(currentVisit);
                            details.setInGym(false);
                            userDetailsService.saveUserDetails(details);
                            return "GYM EXIT";
                        } else {
                            GymVisit currentVisit = GymVisit.builder()
                                    .user(details)
                                    .enterTime(dateTime)
                                    .gym(gym)
                                    .build();
                            gymVisitService.saveGymVisit(currentVisit);
                            details.setInGym(true);
                            userDetailsService.saveUserDetails(details);
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

    private boolean checkMembershipValid(UserDetails details, LocalDateTime dateTime) {
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
                    details.setActive(false);
                    return false;
                }
            }
        }
    }
}

