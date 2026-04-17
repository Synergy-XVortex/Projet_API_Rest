package com.eseo.academic.controller;

import com.eseo.academic.entity.Role;
import com.eseo.academic.entity.User; // Ton entité JPA
import com.eseo.academic.repository.UserRepository;
import com.eseo.academic.security.JwtUtils;
import jakarta.validation.Valid;
import org.openapitools.api.AuthenticationApi;
import org.openapitools.model.AuthToken;
import org.openapitools.model.LoginRequest;
import org.openapitools.model.UserRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController implements AuthenticationApi {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public ResponseEntity<Void> register(@Valid @RequestBody UserRegistration registration) {
        if (userRepository.existsById(registration.getEmail()))
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        User user = new User();
        user.setEmail(registration.getEmail());
        user.setFirstName(registration.getFirstName());
        user.setLastName(registration.getLastName());
        user.setMajor(registration.getMajor());
        user.setRole(Role.valueOf(registration.getRole().name()));
        user.setPassword(passwordEncoder.encode(registration.getPassword()));

        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<AuthToken> login(@Valid @RequestBody LoginRequest loginRequest) {
        return userRepository.findById(loginRequest.getEmail())
                .map(user -> {
                    // Wrong maybe
                    if (!user.isActive())
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).<AuthToken>build();

                    if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                        String token = jwtUtils.generateToken(user.getEmail(), user.getRole().name());
                        AuthToken authToken = new AuthToken();
                        authToken.setToken(token);
                        return ResponseEntity.ok(authToken);
                    }
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).<AuthToken>build();
                })
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
