/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.services;

import com.pdmv.pojo.Major;
import java.util.List;
import java.util.Map;

/**
 *
 * @author phamdominhvuong
 */
public interface MajorService {
    void addOrUpdate(Major major);
    Major getMajorById(int id);
    List<Major> getMajors(Map<String, String> params);
}
