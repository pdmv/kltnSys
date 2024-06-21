/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.services;

import com.pdmv.pojo.SchoolYear;
import java.util.List;

/**
 *
 * @author phamdominhvuong
 */
public interface SchoolYearService {
    void addOrUpdate(SchoolYear schoolYear);
    SchoolYear getSchoolYearById(int id);
    List<SchoolYear> getSchoolYears();
}
