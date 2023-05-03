package com.gildas.gestionstock.auth.controller;

import com.gildas.gestionstock.auth.entity.User;
import com.gildas.gestionstock.auth.payload.request.LoginRequest;
import com.gildas.gestionstock.auth.payload.request.RegisterRequest;
import com.gildas.gestionstock.auth.payload.response.JwtResponse;
import com.gildas.gestionstock.auth.payload.response.MessageResponse;
import com.gildas.gestionstock.auth.repository.UserRepository;
import com.gildas.gestionstock.auth.security.jwt.JwtUtils;
import com.gildas.gestionstock.auth.service.UserDetailsImpl;
import com.gildas.gestionstock.auth.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    AuthenticationManager authenticationManager;


    UserRepository userRepository;

    PasswordEncoder encoder;

    JwtUtils jwtUtils;

    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        User user = userService.getOne(userDetails.getId());

        return ResponseEntity.ok(new JwtResponse(jwt,
                user.getStatus(),
                roles));
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.getUsername())!=null) {
            return ResponseEntity
                    .ok()
                    .body(new MessageResponse("Cette adresse email existe déjà!"));
        }

        if (userRepository.findByTelephone(registerRequest.getTelephone())!=null) {
            return ResponseEntity
                    .ok()
                    .body(new MessageResponse("Ce numéro de téléphone existe déjà!"));
        }

        User user = new User(registerRequest.getNom(), registerRequest.getPrenom(), registerRequest.getUsername(), registerRequest.getTelephone(), registerRequest.getGenre(),
                encoder.encode(registerRequest.getPassword()));

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("success"));
    }

}
