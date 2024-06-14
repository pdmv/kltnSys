/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.controllers;

import com.pdmv.dto.SchoolYearDTO;
import com.pdmv.pojo.SchoolYear;
import com.pdmv.services.SchoolYearService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author phamdominhvuong
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiSchoolYearController {
    @Autowired
    private SchoolYearService schoolYearService;
    
    @GetMapping("/school-years/")
    public ResponseEntity<List<SchoolYearDTO>> list() {
        try {
            return new ResponseEntity<>(this.schoolYearService.getSchoolYears().stream().map(SchoolYearDTO::toSchoolYearDTO).collect(Collectors.toList()), HttpStatus.OK);
        } catch (Exception e) {
            System.err.println(e.getMessage()); 
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }
    
    @GetMapping("/school-years/{id}")
    public ResponseEntity<?> retrive(@PathVariable(value = "id") Integer id) {
        try {
            SchoolYear schoolYear = this.schoolYearService.getSchoolYearById(id);

            if (schoolYear == null) {
                return new ResponseEntity<>("Không tìm thấy niên khóa.", HttpStatus.BAD_REQUEST); 
            }

            return new ResponseEntity<>(SchoolYearDTO.toSchoolYearDTO(schoolYear), HttpStatus.OK);
        } catch (Exception e) {
            System.err.println(e.getMessage()); 
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }
}
