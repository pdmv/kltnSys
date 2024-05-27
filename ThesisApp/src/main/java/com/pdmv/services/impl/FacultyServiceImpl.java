/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.services.impl;

import com.pdmv.pojo.Faculty;
import com.pdmv.repositories.FacultyRepository;
import com.pdmv.services.FacultyService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author phamdominhvuong
 */
@Service
public class FacultyServiceImpl implements FacultyService {
    @Autowired
    private FacultyRepository facultyRepo;

    @Override
    public void addOrUpdate(Faculty faculty) {
        this.facultyRepo.addOrUpdate(faculty);
    }

    @Override
    public Faculty getFacultyById(int id) {
        return this.facultyRepo.getFacultyById(id);
    }

    @Override
    public List<Faculty> getFaculties(String kw) {
        return this.facultyRepo.getFaculties(kw);
    }
    
}
