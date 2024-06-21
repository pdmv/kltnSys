/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.controllers;

import com.pdmv.dto.MajorDTO;
import com.pdmv.pojo.Major;
import com.pdmv.services.MajorService;
import java.util.List;
import java.util.Map;
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
public class ApiMajorController {
    @Autowired
    private MajorService majorService;
    
    @GetMapping("/major/")
    public ResponseEntity<List<MajorDTO>> list(@RequestParam(required = false) Map<String, String> params) {
        try {
            return new ResponseEntity<>(this.majorService.getMajors(params).stream().map(MajorDTO::toMajorDTO).collect(Collectors.toList()), HttpStatus.OK);
        } catch (Exception e) {
            System.err.println(e.getMessage()); 
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }
    
    @GetMapping(path = "/major/{id}/", produces = {
        MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<?> retrieve(@PathVariable(value = "id") Integer id) {
        try {
            Major m = this.majorService.getMajorById(id);
            
            if (m == null) {
                return new ResponseEntity<>("Không tìm thấy ngành nào.", HttpStatus.BAD_REQUEST);
            }
            
            return new ResponseEntity<>(m, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println(e.getMessage()); 
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }
}
