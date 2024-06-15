/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.services.impl;

import com.pdmv.pojo.Criterion;
import com.pdmv.repositories.CriterionRepository;
import com.pdmv.services.CriterionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tid83
 */
@Service
public class CriterionServiceImpl implements CriterionService {

    @Autowired
    private CriterionRepository criterionRepository;

    @Override
    public void addOrUpdate(Criterion criterion) {
        criterionRepository.addOrUpdate(criterion);
    }

    @Override
    public Criterion getCriterionById(int id) {
        return criterionRepository.getCriterionById(id);
    }

    @Override
    public List<Criterion> getCriteria() {
        return criterionRepository.getCriteria();
    }
}
