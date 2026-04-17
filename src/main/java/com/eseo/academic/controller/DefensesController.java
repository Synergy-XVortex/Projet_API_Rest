package com.eseo.academic.controller;

import java.util.List;

import org.openapitools.api.DefensesApi;
import org.openapitools.model.DefenseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.eseo.academic.service.DefenseService;

@RestController
public class DefensesController implements DefensesApi {

    @Autowired
    private DefenseService defenseService;

    @Override
    public ResponseEntity<List<DefenseDTO>> getDefenses() {
        List<DefenseDTO> defenses = defenseService.getAllDefenses();
        return ResponseEntity.ok(defenses);
    }
}
