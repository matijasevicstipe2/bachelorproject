package hr.smatijasevic.bachelorproject.security.user;

import hr.smatijasevic.bachelorproject.groupclass.GroupClass;
import hr.smatijasevic.bachelorproject.membership.MembershipOption;
import hr.smatijasevic.bachelorproject.qr.QRCode;
import hr.smatijasevic.bachelorproject.qr.QRCodeService;
import hr.smatijasevic.bachelorproject.userdetails.UserDetails;
import hr.smatijasevic.bachelorproject.userdetails.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/user/{username}")
    public ResponseEntity<UserDto> getUserInfo(@PathVariable String username) {

        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        if ( accountOptional.isPresent()) {
            UserDetails userDetails = userDetailsService.getUserDetailsByAccount(accountOptional.get());
            userDetails.ge
            UserDto user = UserDto.builder()
                    .profilePicture(accountOptional.get().getProfilePicture())
                    .firstName(accountOptional.get().getFirstName())
                    .lastName(accountOptional.get().getLastName())
                    .qrCode(qrCodeService.getQRCodeByAccount(accountOptional.get()).map(QRCode::getImage).orElse(null))
                    .daysLeft()
                    .membership()
                    .build();

            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private boolean checkMembership(UserDetails details) {
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
