package hr.smatijasevic.bachelorproject.security.user;

import hr.smatijasevic.bachelorproject.membership.MembershipOption;
import hr.smatijasevic.bachelorproject.qr.QRCode;
import hr.smatijasevic.bachelorproject.qr.QRCodeService;
import hr.smatijasevic.bachelorproject.userdetails.UserDetails;
import hr.smatijasevic.bachelorproject.userdetails.UserDetailsService;
import hr.smatijasevic.bachelorproject.visits.GymVisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

import static hr.smatijasevic.bachelorproject.UtilsController.decompressImage;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class UserController {
    private final AccountRepository accountRepository;
    private final UserDetailsService userDetailsService;
    private final QRCodeService qrCodeService;
    private final GymVisitService gymVisitService;

    @GetMapping("/userinfo/{username}")
    public ResponseEntity<UserDto> getUserInfo(@PathVariable String username) {

        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        if ( accountOptional.isPresent()) {
            UserDetails userDetails = userDetailsService.getUserDetailsByAccount(accountOptional.get());
            byte[] qrCode = qrCodeService.getQRCodeByAccount(accountOptional.get()).map(QRCode::getCode).orElse(null);
            byte[] image = null;
            if (accountOptional.get().getProfilePicture() != null) {
                image = decompressImage(accountOptional.get().getProfilePicture());
            }
            UserDto user = UserDto.builder()
                    .profilePicture(image)
                    .firstName(accountOptional.get().getFirstName())
                    .lastName(accountOptional.get().getLastName())
                    .qrCode(qrCode)
                    .qrCodeBase64(Base64.getEncoder().encodeToString(qrCode))
                    .daysLeft(checkMembershipDays(userDetails, LocalDateTime.now()))
                    .active(userDetails.isActive())
                    .build();
            if (userDetails.getMembershipOption() != null) {
                 user.setMembership(userDetails.getMembershipOption().getName());
            }

            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private long checkMembershipDays(UserDetails details, LocalDateTime dateTime) {
        long days;
        MembershipOption membership = details.getMembershipOption();
        if (membership == null) {
            days = -1;
        } else {
            if (membership.getType().equals("T")) {
                days = membership.getDuration() - gymVisitService
                        .getCountByAccountAndEnterTimeAfter(details.getAccount(), details.getPaymentDate());
            } else {
                if (membership.getDuration() == 365) {
                    days = Duration.between(dateTime, details.getPaymentDate().plusYears(1)).toDays();

                } else {
                    days = Duration.between(dateTime, details.getPaymentDate().plusMonths(1)).toDays();
                }
            }
        }

        if (days < 0) {
            details.setActive(false);
        }
        return days;
    }

}
