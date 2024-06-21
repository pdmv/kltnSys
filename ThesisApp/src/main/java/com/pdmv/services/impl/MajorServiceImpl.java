/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.services.impl;

import com.pdmv.pojo.Major;
import com.pdmv.repositories.MajorRepositoty;
import com.pdmv.services.MajorService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author phamdominhvuong
 */
@Service
public class MajorServiceImpl implements MajorService {
    @Autowired
    private MajorRepositoty majorRepo;

    @Override
    public void addOrUpdate(Major major) {
        this.majorRepo.addOrUpdate(major);
    }

    @Override
    public Major getMajorById(int id) {
        return this.majorRepo.getMajorById(id);
    }

    @Override
    public List<Major> getMajors(Map<String, String> params) {
        if (params == null) {
            params = new HashMap<>();
        }
        return this.majorRepo.getMajors(params);
    }
    
}
