package hr.smatijasevic.bachelorproject.email;

import hr.smatijasevic.bachelorproject.personaltrainer.PersonalTrainer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send-email")
    public void sendEmail(@RequestBody PersonalTrainer trainer) throws MessagingException, IOException {
        String toEmail = trainer.getEmail();
        String subject = "Training Session Confirmation";
        String body = "Dear " + trainer.getFirstname() + ",\n\nI would like to schedule training session with you.";

        emailService.sendEmail(toEmail, subject, body);
    }
}

