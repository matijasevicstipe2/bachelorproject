package hr.smatijasevic.bachelorproject.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private String token;
    private Long amount;
    private String currency;
    private String description;

}

