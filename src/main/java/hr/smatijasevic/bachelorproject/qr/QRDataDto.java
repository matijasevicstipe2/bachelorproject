package hr.smatijasevic.bachelorproject.qr;

import lombok.Data;

import java.time.LocalDate;

@Data
public class QRDataDto {
    private String data;
    private LocalDate datetime;
}
