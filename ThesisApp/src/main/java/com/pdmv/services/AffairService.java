/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.services;

import com.pdmv.pojo.Affair;
import java.util.List;
import java.util.Map;

/**
 *
 * @author phamdominhvuong
 */
public interface AffairService {
    void addOrUpdate(Affair affair);
    List<Affair> getAffairs(Map<String, String> params);
    Affair getAffairById(int id);
    Affair getAffairByAccountId(int id);
}
