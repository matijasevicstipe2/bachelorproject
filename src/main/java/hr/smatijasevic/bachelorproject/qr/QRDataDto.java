package hr.smatijasevic.bachelorproject.qr;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QRDataDto {
    private String data;
    private LocalDateTime datetime;
    private Long gymId;
}
