package com.gildas.gestionstock.auth.controller;

import com.gildas.gestionstock.auth.entity.User;
import com.gildas.gestionstock.auth.response.MessageResponse;
import com.gildas.gestionstock.auth.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/profile")
@AllArgsConstructor
public class UserProfileController {

    private final UserService userService;

    @PutMapping("/change-profile-picture/{id}")
    public void changePhoto(@PathVariable("id") int id, @RequestParam("file") MultipartFile file) throws IOException {

        User u = new User();
        u.setPhoto(Base64.getEncoder().encodeToString(file.getBytes()));
        userService.changeProfilePicture(id, u.getPhoto());

    }

    @PutMapping("/update-profile")
    public ResponseEntity<?> updateProfile(@RequestBody User user) {
        return ResponseEntity
                .ok()
                .body(new MessageResponse(userService.updateProfile(user)));
    }

    @PutMapping("/update-username")
    public String updateUsername(@RequestBody User user) {

        return userService.updateUsername(user);

    }

    @PutMapping("/update-telephone")
    public String updateTelephone(@RequestBody User user) {
        return userService.updateTelephone(user);
    }

}
