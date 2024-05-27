/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.services.impl;

import com.pdmv.pojo.SchoolYear;
import com.pdmv.repositories.SchoolYearRepository;
import com.pdmv.services.SchoolYearService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author phamdominhvuong
 */
@Service
public class SchoolYearServiceImpl implements SchoolYearService {
    @Autowired
    private SchoolYearRepository schoolYearRepo;

    @Override
    public void addOrUpdate(SchoolYear schoolYear) {
        this.schoolYearRepo.addOrUpdate(schoolYear);
    }

    @Override
    public SchoolYear getSchoolYearById(int id) {
        return this.schoolYearRepo.getSchoolYearById(id);
    }

    @Override
    public List<SchoolYear> getSchoolYears() {
        return this.schoolYearRepo.getSchoolYears();
    }
    
}
