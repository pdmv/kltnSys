/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.pdmv.services;

import com.pdmv.pojo.Criterion;
import java.util.List;

/**
 *
 * @author tid83
 */
public interface CriterionService {
    void addOrUpdate(Criterion criterion);
    Criterion getCriterionById(int id);
    List<Criterion> getCriteria();
}
