package gr.hua.admin_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendStatusChangeEmail(String citizenUsername, String status, String decisionNote) {
        var message = new SimpleMailMessage();
        message.setTo(citizenUsername + "@example.com");
        message.setSubject("Ενημέρωση για την αίτηση συνταξιοδότησής σας");
        message.setText("Η αίτησή σας άλλαξε κατάσταση σε: " + status + (decisionNote != null ? "\nΣχόλιο: " + decisionNote : ""));
        javaMailSender.send(message);
    }
}