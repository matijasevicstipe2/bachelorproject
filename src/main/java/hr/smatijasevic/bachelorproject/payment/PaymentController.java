package hr.smatijasevic.bachelorproject.payment;

import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import hr.smatijasevic.bachelorproject.membership.MembershipOption;
import hr.smatijasevic.bachelorproject.membership.MembershipOptionService;
import hr.smatijasevic.bachelorproject.security.user.Account;
import hr.smatijasevic.bachelorproject.security.user.AccountRepository;
import hr.smatijasevic.bachelorproject.userdetails.UserDetails;
import hr.smatijasevic.bachelorproject.userdetails.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class PaymentController {

    private final AccountRepository accountRepository;
    private final UserDetailsService userDetailsService;
    private final MembershipOptionService membershipOptionService;

    @Value("${stripe.secretKey}")
    private String stripeSecretKey;


    @PostMapping("/pay")
    public ResponseEntity<String> processPayment(@RequestBody PaymentRequest paymentRequest) throws APIConnectionException, APIException, AuthenticationException, InvalidRequestException, CardException {
        String token = paymentRequest.getToken();
        Long amount = paymentRequest.getAmount();
        String currency = paymentRequest.getCurrency();
        String description = paymentRequest.getDescription();
        String username = paymentRequest.getUsername();
        Long option = paymentRequest.getOption();

        Stripe.apiKey = stripeSecretKey;

        Optional<Account> acc = accountRepository.findByUsername(username);
        MembershipOption membershipOption = membershipOptionService.getMembershipOptionById(option);
        Map<String, Object> customerParams = new HashMap<>();
        customerParams.put("email", acc.get().getEmail());
        customerParams.put("source", token);
        Customer customer = Customer.create(customerParams);
        System.out.println(customer);

        try {
            Charge charge = chargeCustomerCard(customer.getId(), amount, currency, description);
            System.out.println(charge);
            UserDetails user = userDetailsService.getUserDetailsByAccount(acc.get().getId());

            user.setPaymentDate(LocalDate.now());
            user.setUsage(user.getUsage() + 1);
            user.setMembershipOption(membershipOption);

            userDetailsService.saveUserDetails(user);
            return ResponseEntity.ok("Payment successful");
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment failed: " + e.getMessage());
        }
    }

    private Charge chargeCustomerCard(String customerId, Long amount, String currency, String description) throws Exception {
        String sourceCard = Customer.retrieve(customerId).getDefaultSource();
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", (int)(amount * 100));
        chargeParams.put("currency", currency);
        chargeParams.put("customer", customerId);
        chargeParams.put("source", sourceCard);
        chargeParams.put("description", description);
        Charge charge = Charge.create(chargeParams);
        return charge;
    }
}
