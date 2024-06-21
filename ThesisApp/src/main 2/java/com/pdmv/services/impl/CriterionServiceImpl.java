/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.services.impl;

import com.pdmv.pojo.Criterion;
import com.pdmv.repositories.CriterionRepository;
import com.pdmv.services.CriterionService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author phamdominhvuong
 */
@Service
public class CriterionServiceImpl implements CriterionService {
    @Autowired
    private CriterionRepository criterionRepo;

    @Override
    public int addOrUpdate(Criterion criterion) {
        if (criterion.getActive() == null) {
            criterion.setActive(true);
        }
        
        if (criterion.getId() != null) {
            Criterion old = this.criterionRepo.getCriterionById(criterion.getId());
            criterion.setCreatedDate(old.getCreatedDate());
        }
        
        if (criterion.getFacultyId() == null) {
            criterion.setFacultyId(criterion.getAffairId().getFacultyId());
        }
        
        return this.criterionRepo.addOrUpdate(criterion);
    }

    @Override
    public List<Criterion> list(Map<String, String> params) {
        return this.criterionRepo.list(params);
    }

    @Override
    public Criterion getCriterionById(int id) {
        return this.criterionRepo.getCriterionById(id);
    }
    
}
