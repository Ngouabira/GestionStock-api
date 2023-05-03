package com.gildas.gestionstock.email;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.UnsupportedEncodingException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/email")
@AllArgsConstructor
public class EmailController {

    EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<?> sendEmail(EmailRequest emailRequest) throws MessagingException, UnsupportedEncodingException {
        emailService.sendEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getBody());
        return ResponseEntity.ok(new MessageResponse("Ok"));
    }
}
