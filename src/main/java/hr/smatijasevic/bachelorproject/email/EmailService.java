package hr.smatijasevic.bachelorproject.email;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.io.IOException;

public interface EmailService {
    void sendEmail(String to, String subject, String message) throws MessagingException, IOException;
    void sendEmailQRCode(String to, String subject, String message, byte[] qrCodeBytes) throws MessagingException, IOException;
}
