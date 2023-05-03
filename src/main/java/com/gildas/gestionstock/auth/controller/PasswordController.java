package com.gildas.gestionstock.auth.controller;

import com.gildas.gestionstock.auth.entity.Token;
import com.gildas.gestionstock.auth.entity.User;
import com.gildas.gestionstock.auth.payload.request.ChangePasswordRequest;
import com.gildas.gestionstock.auth.payload.response.MessageResponse;
import com.gildas.gestionstock.auth.service.TokenService;
import com.gildas.gestionstock.auth.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/password")
@AllArgsConstructor
public class PasswordController {

    private final UserService userService;

    private final TokenService tokenService;

    //private final SmsService smsService;

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody String username) {

        String otp = new DecimalFormat("000000").format(Math.random() * 999999);
        User user = userService.getByUsername(username);

        if (user == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("User not found"));
        }

        tokenService.save(new Token(otp, user));

        //smsService.sendSms(new SmsRequest(user.getTelephone(), "Utilisez  ce code :" + otp + " pour réinitialiser votre mot de passe"));

        return ResponseEntity.badRequest().body(new MessageResponse("OK"));
    }

    @PostMapping("/reset-password/{token}")
    public ResponseEntity<?> resetPassword(@PathVariable("token") String token, @RequestBody String password) {

        Token token1 = tokenService.findByToken(token);

        if (token1 == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Token not found"));
        } else if (token1.isExpired()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Token expired"));
        } else {
            User user = token1.getUser();

            userService.updateUserPassword(user, password);

            return ResponseEntity.badRequest().body(new MessageResponse("OK"));
        }
    }

    @PutMapping("/update-password")
    public ResponseEntity<?> updatePasword(@RequestBody ChangePasswordRequest cp) {
        return ResponseEntity
                .ok()
                .body(new MessageResponse(userService.updatePassword(cp.getId(), cp.getOldPassword(), cp.getNewPassword())));
    }
}
