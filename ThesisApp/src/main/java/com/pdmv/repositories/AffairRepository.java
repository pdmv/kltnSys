/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.repositories;

import com.pdmv.pojo.Affair;
import java.util.List;
import java.util.Map;

/**
 *
 * @author phamdominhvuong
 */
public interface AffairRepository {
    void addOrUpdate(Affair affair);
    List<Affair> getAffairs(Map<String, String> params);
    Affair getAffairById(int id); 
}
