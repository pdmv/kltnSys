/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.controllers;

import com.pdmv.dto.CriterionDTO;
import com.pdmv.pojo.Criterion;
import com.pdmv.services.CriterionService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author tid83
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiCriterionController {

    @Autowired
    private CriterionService criterionService;

    @GetMapping("/criteria")
    public ResponseEntity<List<CriterionDTO>> list() {
        try {
            return new ResponseEntity<>(criterionService.getCriteria().stream()
                    .map(CriterionDTO::toCriterionDTO)
                    .collect(Collectors.toList()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/criteria/{id}")
    public ResponseEntity<?> retrieve(@PathVariable(value = "id") Integer id) {
        try {
            Criterion criterion = criterionService.getCriterionById(id);
            if (criterion == null) {
                return new ResponseEntity<>("Criterion not found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(CriterionDTO.toCriterionDTO(criterion), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/criteria")
    public ResponseEntity<?> create(@RequestBody CriterionDTO criterionDTO) {
        try {
            Criterion criterion = new Criterion();
            criterion.setName(criterionDTO.getName());
            criterion.setDescription(criterionDTO.getDescription());
            // Set the affairId if necessary

            criterionService.addOrUpdate(criterion);
            return new ResponseEntity<>(CriterionDTO.toCriterionDTO(criterion), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
