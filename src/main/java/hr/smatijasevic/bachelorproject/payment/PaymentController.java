package hr.smatijasevic.bachelorproject.payment;

import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Card;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.ExternalAccount;
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
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
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
    public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest paymentRequest) {
        String token = paymentRequest.getToken();
        Long amount = paymentRequest.getAmount();
        String currency = paymentRequest.getCurrency();
        String description = paymentRequest.getDescription();
        String username = paymentRequest.getUser();
        Long option = paymentRequest.getOption();
        Stripe.apiKey = stripeSecretKey;

        Optional<Account> acc = accountRepository.findByUsername(username);
        if (acc.isEmpty()) {
            PaymentResponse response = PaymentResponse.builder()
                    .success(false)
                    .message("Payment failed: " + "Account not found")
                    .charge(null)
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Account account = acc.get();
        MembershipOption membershipOption = membershipOptionService.getMembershipOptionById(option);
        if (membershipOption == null) {
            PaymentResponse response = PaymentResponse.builder()
                    .success(false)
                    .message("Payment failed: " + "Membership option not found")
                    .charge(null)
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        try {
            Customer customer;
            if (account.getStripeCustomerId() != null) {
                customer = Customer.retrieve(account.getStripeCustomerId());
                // Check if the card already exists for the customer
                if (!doesCardAlreadyExists(customer, token)) {
                    // Create and add the new card to the customer's account
                    addCardToCustomer(customer, token);
                }
            } else {
                Map<String, Object> customerParams = new HashMap<>();
                customerParams.put("email", account.getEmail());
                customerParams.put("source", token);
                customer = Customer.create(customerParams);
                account.setStripeCustomerId(customer.getId());
                accountRepository.save(account);
            }
            Charge charge = chargeCustomerCard(customer.getId(), amount, currency, description);
            System.out.println(charge);

            UserDetails user = userDetailsService.getUserDetailsByAccount(account);
            user.setPaymentDate(LocalDateTime.now());
            user.setUsage(user.getUsage() + 1);
            user.setMembershipOption(membershipOption);
            user.setActive(true);

            userDetailsService.saveUserDetails(user);
            PaymentResponse response = PaymentResponse.builder()
                    .success(true)
                    .message("Payment successful")
                    .charge(charge)
                    .build();

            return ResponseEntity.ok(response);
        } catch (StripeException e) {
            PaymentResponse response = PaymentResponse.builder()
                    .success(false)
                    .message("Payment failed: " + e.getMessage())
                    .charge(null)
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    private boolean doesCardAlreadyExists(Customer customer, String token) {
        List<ExternalAccount> externalAccounts = customer.getSources().getData();
        for (ExternalAccount externalAccount : externalAccounts) {
            if (externalAccount instanceof Card) {
                Card card = (Card) externalAccount;
                if (card.getId().equals(token)) {
                    return true;
                }
            }
        }
        return false;
    }


    private void addCardToCustomer(Customer customer, String token) throws StripeException {
        Map<String, Object> sourceParams = new HashMap<>();
        sourceParams.put("source", token);
        customer.getSources().create(sourceParams);
    }

    private Charge chargeCustomerCard(String customerId, Long amount, String currency, String description) throws StripeException {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", (int) (amount * 100));
        chargeParams.put("currency", currency);
        chargeParams.put("customer", customerId);
        chargeParams.put("description", description);
        Charge charge = Charge.create(chargeParams);
        return charge;
    }


}
