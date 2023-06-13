package hr.smatijasevic.bachelorproject.payment;

import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class PaymentController {

    @Value("${stripe.secretKey}")
    private String stripeSecretKey;


    @PostMapping("/pay")
    public ResponseEntity<String> processPayment(@RequestBody PaymentRequest paymentRequest) throws APIConnectionException, APIException, AuthenticationException, InvalidRequestException, CardException {
        String token = paymentRequest.getToken();
        Long amount = paymentRequest.getAmount();
        String currency = paymentRequest.getCurrency();
        String description = paymentRequest.getDescription();
        String email = paymentRequest.getEmail();

        Stripe.apiKey = stripeSecretKey;

        Map<String, Object> params = new HashMap<>();
        params.put("amount", (int)(amount * 100));
        params.put("currency", currency);
        params.put("source", token);
        params.put("description", description);

        Map<String, Object> customerParams = new HashMap<>();
        customerParams.put("email", email);
        customerParams.put("source", token);
        Customer customer = Customer.create(customerParams);
        System.out.println(customer);

        try {
            Charge charge = Charge.create(params);
            System.out.println(charge);
            return ResponseEntity.ok("Payment successful");
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment failed: " + e.getMessage());
        }
    }
    public Charge chargeCustomerCard(String customerId, int amount) throws Exception {
        String sourceCard = Customer.retrieve(customerId).getDefaultSource();
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", amount);
        chargeParams.put("currency", "USD");
        chargeParams.put("customer", customerId);
        chargeParams.put("source", sourceCard);
        Charge charge = Charge.create(chargeParams);
        return charge;
    }
}
