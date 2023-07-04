package hr.smatijasevic.bachelorproject.payment;

import com.stripe.model.Charge;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponse {
    private boolean success;
    private String message;
    private Charge charge;
}

