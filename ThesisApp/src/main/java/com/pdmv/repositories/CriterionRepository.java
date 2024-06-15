/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.pdmv.repositories;

import com.pdmv.pojo.Criterion;
import java.util.List;

/**
 *
 * @author tid83
 */
public interface CriterionRepository {
    void addOrUpdate(Criterion criterion);
    Criterion getCriterionById(int id);
    List<Criterion> getCriteria();
}
