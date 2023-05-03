package com.gildas.gestionstock.auth.controller;

import com.gildas.gestionstock.auth.entity.User;
import com.gildas.gestionstock.auth.payload.request.ChangePasswordRequest;
import com.gildas.gestionstock.auth.payload.response.MessageResponse;
import com.gildas.gestionstock.auth.service.UserService;
import com.gildas.gestionstock.auth.AppConfiguration;
import com.gildas.gestionstock.email.EmailService;
import io.jsonwebtoken.Jwts;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {


    private final UserService userService;

    private EmailService emailService;

    private final AppConfiguration appConfiguration;

    @GetMapping("/all")
    public List<User> read() {

        return userService.read();

    }

    @PostMapping("/create")
    public String create(@RequestBody User user) {

        return userService.create(user);

    }

    @PutMapping("/update-status/{id}")
    public void updateStatus(@PathVariable("id") int id, @RequestBody String status) {

         userService.updateStatus(id, status);
    }

    @GetMapping("get-by-token/{token}")
    public User getByToken(@PathVariable("token") String token) {

        if (Objects.equals(token, "") || token == null) {

            return null;
        } else {

            try {
                String username = Jwts.parser().setSigningKey(appConfiguration.getJwtSecret()).parseClaimsJws(token).getBody().getSubject();
				return userService.getByUsername(username);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return null;
        }

    }

	@GetMapping("/one/{id}")
	public User getOne(@PathVariable("id") int id) {

		return userService.getOne(id);
	}


	@PutMapping("/change-profile-picture/{id}")
	public void addPhoto(@PathVariable("id") int id, @RequestParam("file") MultipartFile file) throws IOException {

		User u = new User();
		u.setPhoto(Base64.getEncoder().encodeToString(file.getBytes()));
		userService.addProfilePicture(id, u.getPhoto());

	}

    @PutMapping("/update-profile")
    public ResponseEntity<?> updateProfile(@RequestBody User user) {
        return ResponseEntity
                .ok()
                .body(new MessageResponse(userService.updateProfile(user)));
    }

    @PutMapping("/update-email")
    public String updateEmail(@RequestBody User user) {

        return userService.updateUsername(user);

    }


    @PutMapping("/update-telephone")
    public String updateTelephone(@RequestBody User user) {
        return userService.updateTelephone(user);
    }

    @PutMapping("/update-password")
    public ResponseEntity<?> updatePasword(@RequestBody ChangePasswordRequest cp) {
        return ResponseEntity
                .ok()
                .body(new MessageResponse(userService.updatePassword(cp.getId(), cp.getOldPassword(), cp.getNewPassword())));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody String recipientEmail) throws UnsupportedEncodingException, MessagingException {
		String token = UUID.randomUUID().toString();

		String test = userService.updateResetPasswordToken(token, recipientEmail);

        if (Objects.equals(test, "ok")) {

            String resetPasswordLink = appConfiguration.getFrontLink() + "/reset-password/" + token;

            String content = "<p>Salut,</p>"
                    + "<p>Vous avez demandé la réinitialisation de votre mot de passe.</p>"
                    + "<p>Cliquez sur le lien ci-dessous pour changer votre mot de passe :</p>"
                    + "<p><a href='"+  resetPasswordLink  +"'>Changer mon mot de passe</a></p>"
                    + "<br>"
                    + "<p>Ignorez cet e-mail si vous vous souvenez de votre mot de passe, "
                    + "ou vous n'avez pas fait la demande.</p>";

            emailService.sendEmail(recipientEmail, "Mot de passe oublié", content);

            return ResponseEntity
                    .ok()
                    .body(new MessageResponse("ok"));
        } else {

            return ResponseEntity
                    .ok()
                    .body(new MessageResponse("Email inexistant"));

        }

    }

    @PostMapping("/reset-password/{token}")
    public ResponseEntity<?> resetPassword(@PathVariable("token") String token, @RequestBody String passwordtk) {
		User user = userService.getByResetPasswordToken(token);
        if (user == null) {
            return ResponseEntity
                    .ok()
                    .body(new MessageResponse("error"));
        } else {

            userService.updateUserPassword(user, passwordtk);
            return ResponseEntity
                    .ok()
                    .body(new MessageResponse("ok"));

        }
    }


}
