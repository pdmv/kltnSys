/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.services.impl;

import com.pdmv.pojo.Class;
import com.pdmv.repositories.ClassRepository;
import com.pdmv.services.ClassService;
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
public class ClassServiceImpl implements ClassService {
    @Autowired
    private ClassRepository classRepo;

    @Override
    public void addOrUpdate(Class myClass) {
        this.classRepo.addOrUpdate(myClass);
    }

    @Override
    public Class getClassById(int id) {
        return this.classRepo.getClassById(id);
    }

    @Override
    public List<Class> getClasses(Map<String, String> params) {
        if (params == null) {
            params = new HashMap<>();
        }
        return this.classRepo.getClasses(params);
    }
    
}
