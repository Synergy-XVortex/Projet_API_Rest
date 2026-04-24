package com.eseo.academic.controller;

import com.eseo.academic.service.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.openapitools.api.AdministratorOnlyApi;
import org.openapitools.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ADMINISTRATOR')")
public class AdministratorOnlyController implements AdministratorOnlyApi {

    @Autowired private UserService userService;
    @Autowired private CompanyService companyService;
    @Autowired private InternshipService internshipService;
    @Autowired private DefenseService defenseService;

    @Override
    public ResponseEntity<Void> createUser(@Valid @RequestBody UserRegistration userRegistration) {
        userService.registerNewUser(userRegistration);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<UserDTO>> getUsers(@Valid String role, @Valid String major) {
        List<UserDTO> users = userService.findUsersByCriteria(role, major);
        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<Void> createCompany(@Valid @RequestBody CompanyDTO companyDTO) {
        companyService.saveCompany(companyDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> createInternship(@Valid @RequestBody InternshipDTO internshipDTO) {
        internshipService.createNewInternship(internshipDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> createDefense(@Valid @RequestBody DefenseDTO defenseDTO) {
        defenseService.scheduleDefense(defenseDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> updateInternship(@NotNull Integer id, @Valid @RequestBody InternshipDTO internshipDTO) {
        internshipService.updateInternship(id.longValue(), internshipDTO);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteInternship(@NotNull Integer id) {
        internshipService.deleteInternship(id.longValue());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteUser(@NotNull String email) {
        userService.removeUser(email);
        return ResponseEntity.noContent().build();
    }

    // LA CORRECTION VITALE EST ICI : Ajout du @RequestBody !
    @Override
    public ResponseEntity<Void> updateUser(@NotNull String email, @Valid @RequestBody UserDTO userDTO) {
        userService.updateUser(email, userDTO);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> activateUser(@NotNull String email) {
        userService.activateUser(email);
        return ResponseEntity.noContent().build();
    }
}