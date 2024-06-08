/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.controllers;

import com.pdmv.dto.FacultyDTO;
import com.pdmv.pojo.Faculty;
import com.pdmv.services.FacultyService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
public class ApiFacultyController {
    @Autowired
    private FacultyService facultyService;
    
    @GetMapping("/faculty/")
    public ResponseEntity<List<FacultyDTO>> list(@RequestParam(required = false) String kw) {
        try {
            if (kw == null) {
                kw = "";
            }
            return new ResponseEntity<>(this.facultyService.getFaculties(kw).stream().map(FacultyDTO::toFacultyDTO).collect(Collectors.toList()), HttpStatus.OK);
        } catch (Exception e) {
            System.err.println(e.getMessage()); 
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }
    
    @GetMapping(path = "/faculty/{id}/", produces = {
        MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<?> retrieve(@PathVariable(value = "id") Integer id) {
        try {
            Faculty f = this.facultyService.getFacultyById(id);
            
            if (f == null) {
                return new ResponseEntity<>("Không tìm thấy khoa nào.", HttpStatus.BAD_REQUEST);
            }
            
            return new ResponseEntity<>(f, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println(e.getMessage()); 
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }
}
