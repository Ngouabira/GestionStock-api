package com.gildas.gestionstock.email;

import com.gildas.gestionstock.auth.AppConfiguration;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.io.UnsupportedEncodingException;

@Service
@AllArgsConstructor
public class EmailService {

    private final AppConfiguration appConfiguration;
    private final JavaMailSender mailSender;
    public void sendEmail(String to, String subject, String body) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(appConfiguration.getEmail(), appConfiguration.getAppname());
        helper.setTo(to);

        helper.setSubject(subject);

        helper.setText(body, true);

        mailSender.send(message);

    }
}
