/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.controllers;

import com.pdmv.dto.ThesisDTO;
import com.pdmv.dto.CreateThesisDTO;
import com.pdmv.dto.ThesisDetailsDTO;
import com.pdmv.dto.ThesisLecturerDTO;
import com.pdmv.dto.ThesisStudentDTO;
import com.pdmv.services.ThesisService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author phamdominhvuong
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiThesisController {
    @Autowired
    private ThesisService thesisService;
    
    @PostMapping(path = "/thesis/", produces = {
        MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<?> create(@RequestBody CreateThesisDTO thesisDTO) {
        
        try {
            for (ThesisLecturerDTO lecturer : thesisDTO.getThesisLecturerSet()) {
                System.out.println(lecturer.getLecturerId());
            }

            for (ThesisStudentDTO lecturer : thesisDTO.getThesisStudentSet()) {
                System.out.println(lecturer.getStudentId());
            }
            
            this.thesisService.addOrUpdate(thesisDTO);
            
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST); 
        }
        
    }
    
    @GetMapping(path = "/thesis/{id}/", produces = {
        MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<ThesisDetailsDTO> retrieve(@PathVariable(value = "id") Integer id) {
        ThesisDetailsDTO thesisDTO = this.thesisService.getThesisById(id);

        if (thesisDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(thesisDTO, HttpStatus.OK);
    }
    
    @GetMapping(path = "/thesis/", produces = {
        MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<List<ThesisDTO>> list(@RequestParam Map<String, String> params) {
        List<ThesisDTO> list = this.thesisService.getLists(params);

        if (list == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
