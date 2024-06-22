/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.components;

import com.pdmv.pojo.Faculty;
import com.pdmv.services.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author phamdominhvuong
 */
@Component
public class StringToFacultyConverter implements Converter<String, Faculty> {

    @Autowired
    private FacultyService facultyService; 

    @Override
    public Faculty convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }

        try {
            int facultyId = Integer.parseInt(source);
            return facultyService.getFacultyById(facultyId);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
