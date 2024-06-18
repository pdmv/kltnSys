/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.repositories;

import com.pdmv.pojo.Criterion;
import java.util.List;
import java.util.Map;

/**
 *
 * @author phamdominhvuong
 */
public interface CriterionRepository {
    int addOrUpdate(Criterion criterion);
    List<Criterion> list(Map<String, String> params);
    Criterion getCriterionById(int id);
}
