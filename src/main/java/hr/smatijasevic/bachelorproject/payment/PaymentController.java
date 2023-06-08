package hr.smatijasevic.bachelorproject.payment;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
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
    public ResponseEntity<String> processPayment(@RequestBody PaymentRequest paymentRequest) {
        String token = paymentRequest.getToken();
        Long amount = paymentRequest.getAmount();
        String currency = paymentRequest.getCurrency();
        String description = paymentRequest.getDescription();

        Stripe.apiKey = stripeSecretKey;

        Map<String, Object> params = new HashMap<>();
        params.put("amount", amount);
        params.put("currency", currency);
        params.put("source", token);
        params.put("description", description);

        try {
            Charge charge = Charge.create(params);
            // Payment successful, you can perform additional actions here
            return ResponseEntity.ok("Payment successful");
        } catch (StripeException e) {
            // Payment failed, handle the error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment failed: " + e.getMessage());
        }
    }
}
