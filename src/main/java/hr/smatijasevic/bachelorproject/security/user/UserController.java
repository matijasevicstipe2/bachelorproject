package hr.smatijasevic.bachelorproject.security.user;

import hr.smatijasevic.bachelorproject.groupclass.GroupClass;
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
import java.util.Optional;

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
            UserDto user = UserDto.builder()
                    .profilePicture(accountOptional.get().getProfilePicture())
                    .firstName(accountOptional.get().getFirstName())
                    .lastName(accountOptional.get().getLastName())
                    .qrCode(qrCodeService.getQRCodeByAccount(accountOptional.get()).map(QRCode::getCode).orElse(null))
                    .daysLeft(checkMembershipDays(userDetails, LocalDateTime.now()))
                    .membership(userDetails.getMembershipOption().getName())
                    .build();

            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private long checkMembershipDays(UserDetails details, LocalDateTime dateTime) {
        long days;
        MembershipOption membership = details.getMembershipOption();
        if (membership.getType().equals("T")) {
            days = membership.getDuration() - gymVisitService
                    .getCountByAccountAndEnterTimeAfter(details.getAccount(), details.getPaymentDate());
        } else {
            if (membership.getDuration() == 365) {
                days = Duration.between(details.getPaymentDate().plusYears(1), dateTime).toDays();

            } else {
                days = Duration.between(details.getPaymentDate().plusMonths(1), dateTime).toDays();
            }
        }
        if (days < 0) {
            details.setActive(false);
        }
        return days;
    }
}
