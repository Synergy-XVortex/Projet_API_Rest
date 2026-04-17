package com.eseo.academic.controller;

import org.openapitools.api.UsersApi;
import org.openapitools.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import com.eseo.academic.service.UserService;

import jakarta.validation.constraints.NotNull;

@RestController
public class UsersController implements UsersApi {

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<UserDTO> getUserByEmail(@NotNull String email) {
        UserDTO user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<UserDTO> getMyProfile() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserDTO user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<UserDTO> updateMyProfile(UserDTO userDTO) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserDTO updated = userService.updateUserPersonal(email, userDTO);
        return ResponseEntity.ok(updated);
    }
}
