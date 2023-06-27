package hr.smatijasevic.bachelorproject.email;

public interface EmailService {
    void sendEmail(String toEmail, String subject, String body);
}
